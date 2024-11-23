package tn.esprit.services;

import tn.esprit.interfaces.IServiceComment;
import tn.esprit.models.Comment;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServiceComment implements IServiceComment<Comment> {
    private Connection cnx;

    public ServiceComment() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Comment comment) {
        String query = "INSERT INTO `comment`(`content`, `created_at`, `post_id`, `user_id`) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setDate(2, java.sql.Date.valueOf(comment.getCreatedAt())); // Convert LocalDate to SQL Date
            preparedStatement.setInt(3, comment.getPostId());
            preparedStatement.setInt(4, comment.getUserId());

            preparedStatement.executeUpdate();
            System.out.println("Comment added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding comment: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Comment> getAll() {
        ArrayList<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM `comment`";
        try (Statement statement = cnx.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setContent(resultSet.getString("content"));
                comment.setCreatedAt(resultSet.getDate("created_at").toLocalDate()); // Convert SQL Date to LocalDate
                comment.setPostId(resultSet.getInt("post_id"));
                comment.setUserId(resultSet.getInt("user_id"));

                comments.add(comment);
            }
        } catch (SQLException e) {
            System.out.println("Error getting comments: " + e.getMessage());
        }
        return comments;
    }

    @Override
    public void update(Comment comment) {
        String query = "UPDATE `comment` SET content=?, created_at=?, post_id=?, user_id=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setDate(2, java.sql.Date.valueOf(comment.getCreatedAt())); // Convert LocalDate to SQL Date
            preparedStatement.setInt(3, comment.getPostId());
            preparedStatement.setInt(4, comment.getUserId());
            preparedStatement.setInt(5, comment.getId());

            preparedStatement.executeUpdate();
            System.out.println("Comment updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating comment: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Comment comment) {
        String query = "DELETE FROM `comment` WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, comment.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Comment deleted successfully.");
                return true;
            } else {
                System.out.println("No comment found with the given ID.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting comment: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Comment findById(int id) {
        Comment comment = null;
        String query = "SELECT * FROM `comment` WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    comment = new Comment();
                    comment.setId(resultSet.getInt("id"));
                    comment.setContent(resultSet.getString("content"));
                    comment.setCreatedAt(resultSet.getDate("created_at").toLocalDate()); // Convert SQL Date to LocalDate
                    comment.setPostId(resultSet.getInt("post_id"));
                    comment.setUserId(resultSet.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding comment by ID: " + e.getMessage());
        }
        return comment;
    }

    @Override
    public ArrayList<Comment> getByPostId(int postId) {
        ArrayList<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM `comment` WHERE post_id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, postId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Comment comment = new Comment();
                    comment.setId(resultSet.getInt("id"));
                    comment.setContent(resultSet.getString("content"));
                    comment.setCreatedAt(resultSet.getDate("created_at").toLocalDate()); // Convert SQL Date to LocalDate
                    comment.setPostId(resultSet.getInt("post_id"));
                    comment.setUserId(resultSet.getInt("user_id"));

                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting comments by post ID: " + e.getMessage());
        }
        return comments;
    }

    @Override
    public ArrayList<Comment> getByUserId(int userId) {
        ArrayList<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM `comment` WHERE user_id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Comment comment = new Comment();
                    comment.setId(resultSet.getInt("id"));
                    comment.setContent(resultSet.getString("content"));
                    comment.setCreatedAt(resultSet.getDate("created_at").toLocalDate()); // Convert SQL Date to LocalDate
                    comment.setPostId(resultSet.getInt("post_id"));
                    comment.setUserId(resultSet.getInt("user_id"));

                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting comments by user ID: " + e.getMessage());
        }
        return comments;
    }
}
