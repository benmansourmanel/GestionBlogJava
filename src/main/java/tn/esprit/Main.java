package tn.esprit;

import tn.esprit.models.Comment;
import tn.esprit.models.Post;
import tn.esprit.services.ServiceComment;
import tn.esprit.services.ServicePost;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        ServiceComment commentService = new ServiceComment();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate createDate = LocalDate.parse("01/01/2000", formatter);
            LocalDate endDate = LocalDate.parse("01/01/2001", formatter);
            // Create a new comment to add
            Comment newComment = new Comment(5, "sqdd",createDate, 2, 3);

            // Add the new comment
            commentService.add(newComment);

            // Print all comment records (before update)
            printAllComments(commentService);

            // Retrieve the ID of the newly added comment
            ArrayList<Comment> allComments = commentService.getAll();
            Comment addedComment = allComments.get(allComments.size() - 1); // Get the last added comment
            int addedCommentId = addedComment.getId();

            // Update the added comment
            commentService.update(addedComment);

            // Print all comment records (after update)
            printAllComments(commentService);

            // Delete the updated comment
            commentService.delete(addedComment);

            // Print all comment records (after delete)
            printAllComments(commentService);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        /*************************************************Post Part*******************************/
        ServicePost postService = new ServicePost();

        try {
            // Parse dates
            LocalDate startDate = LocalDate.parse("01/01/2000", formatter);
            LocalDate endDate = LocalDate.parse("01/01/2001", formatter);

            // Create a new post to add
            Post newPost = new Post(0, "test", "testtttttttt", "testtt", startDate, endDate); // ID set to 0 to let the database auto-increment

            // Add the new post
            postService.add(newPost);

            // Print all post records (before update)
            printAllPosts(postService);

            // Retrieve the ID of the newly added post
            ArrayList<Post> allPosts = postService.getAll();
            Post addedPost = allPosts.get(allPosts.size() - 1); // Get the last added post
            int addedPostId = addedPost.getId();

            // Update the added post
            addedPost.setContent("manel"); // Example: Change the content
            postService.update(addedPost);

            // Print all post records (after update)
            printAllPosts(postService);

            // Delete the updated post
            postService.delete(addedPost);

            // Print all post records (after delete)
            printAllPosts(postService);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void printAllComments(ServiceComment commentService) {
        System.out.println("Comment records:");
        System.out.println(commentService.getAll());
    }

    private static void printAllPosts(ServicePost postService) {
        System.out.println("Post records:");
        System.out.println(postService.getAll());
    }
}
