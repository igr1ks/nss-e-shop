package cz.cvut.fel.nss.product_service.service;

import cz.cvut.fel.nss.product_service.model.dto.CreateReviewRequest;
import cz.cvut.fel.nss.product_service.model.dto.ReviewDTO;
import cz.cvut.fel.nss.product_service.model.dto.UpdateReviewRequest;
import cz.cvut.fel.nss.product_service.model.entity.Product;
import cz.cvut.fel.nss.product_service.model.entity.Review;
import cz.cvut.fel.nss.product_service.model.utils.ReviewMapper;
import cz.cvut.fel.nss.product_service.repository.ProductRepository;
import cz.cvut.fel.nss.product_service.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;

    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found: " + productId);
        }

        return reviewRepository.findByProductId(productId)
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReviewDTO getReviewById(Long id) {
        Review review = findReviewEntityById(id);
        return reviewMapper.toDto(review);
    }

    public ReviewDTO createReview(Long productId, CreateReviewRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));

        Review review = new Review();
        review.setProduct(product);
        review.setRating(request.rating());
        review.setComment(request.comment());

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }

    public ReviewDTO updateReview(Long id, UpdateReviewRequest request) {
        Review review = findReviewEntityById(id);

        if (request.rating() != null) {
            review.setRating(request.rating());
        }

        if (request.comment() != null) {
            review.editReview(request.comment());
        }

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }

    public void deleteReview(Long id) {
        Review review = findReviewEntityById(id);
        reviewRepository.delete(review);
    }

    private Review findReviewEntityById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found: " + id));
    }
}