package com.Raj.service;

import com.Raj.model.Product;
import com.Raj.model.Review;
import com.Raj.model.User;
import com.Raj.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {

    Review createReview(CreateReviewRequest req ,
                        User user,
                        Product product);
    List<Review> getReviewByProductId(Long productId);

    Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception;

    void deleteReview(Long reviewId, Long userId) throws Exception;

    Review getReviewById(Long reviewId) throws Exception;

}
