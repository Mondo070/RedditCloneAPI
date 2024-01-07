package com.redditclone.reddit.upvote;

import com.redditclone.reddit.post.PostDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

public class BestComparator implements Comparator<PostDto> {

    @Override
    public int compare(PostDto post1, PostDto post2) {
        return Double.compare(hot(post1.upvotes(), post1.dateSubmitted()),
                hot(post2.upvotes(), post2.dateSubmitted()));
    }

    public double hot(Integer upvotes, LocalDateTime dateTime) {
        double order = Math.log10(Math.max(Math.abs(upvotes), 1));
        int sign = (upvotes > 0) ? 1 : (upvotes < 0) ? -1 : 0;
        long seconds = epochSeconds(dateTime) - 1134028003;


        return round(sign * order * seconds / 45000, 7);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private long epochSeconds(LocalDateTime dateTime) {
        LocalDateTime epoch = LocalDate.EPOCH.atTime(0,0,0);

        long days = ChronoUnit.DAYS.between(epoch, dateTime);
        long seconds = ChronoUnit.SECONDS.between(epoch, dateTime);

        return days * 86400 + seconds;
    }

}
