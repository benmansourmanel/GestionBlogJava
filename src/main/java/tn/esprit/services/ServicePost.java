package tn.esprit.services;

import tn.esprit.interfaces.IServicePost;
import tn.esprit.models.Post;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServicePost implements IServicePost<Post> {
    private Connection cnx;

    public ServicePost() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Post post) {
        String query = "INSERT INTO `post`(`title`, `content`, `image`, `created_at`, `update_at`) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setString(3, post.getImage());
            preparedStatement.setDate(4, java.sql.Date.valueOf(post.getCreatedAt())); // Convert LocalDate to SQL Date
            preparedStatement.setDate(5, java.sql.Date.valueOf(post.getUpdatedAt())); // Convert LocalDate to SQL Date

            preparedStatement.executeUpdate();
            System.out.println("Post added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding post: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Post> getAll() {
        ArrayList<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM `post`";
        try (Statement statement = cnx.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setTitle(resultSet.getString("title"));
                post.setContent(resultSet.getString("content"));
                post.setImage(resultSet.getString("image"));
                post.setCreatedAt(resultSet.getDate("created_at").toLocalDate()); // Convert SQL Date to LocalDate
                post.setUpdatedAt(resultSet.getDate("update_at").toLocalDate()); // Convert SQL Date to LocalDate

                posts.add(post);
            }
        } catch (SQLException e) {
            System.out.println("Error getting posts: " + e.getMessage());
        }
        return posts;
    }

    @Override
    public void update(Post post) {
        String query = "UPDATE `post` SET title=?, content=?, image=?, created_at=?, update_at=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setString(3, post.getImage());
            preparedStatement.setDate(4, java.sql.Date.valueOf(post.getCreatedAt())); // Convert LocalDate to SQL Date
            preparedStatement.setDate(5, java.sql.Date.valueOf(post.getUpdatedAt())); // Convert LocalDate to SQL Date
            preparedStatement.setInt(6, post.getId());

            preparedStatement.executeUpdate();
            System.out.println("Post updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating post: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Post post) {
        String query = "DELETE FROM `post` WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, post.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Post deleted successfully.");
                return true;
            } else {
                System.out.println("No post found with the given ID.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting post: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        String query = "SELECT * FROM `post` WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    post = new Post();
                    post.setId(resultSet.getInt("id"));
                    post.setTitle(resultSet.getString("title"));
                    post.setContent(resultSet.getString("content"));
                    post.setImage(resultSet.getString("image"));
                    post.setCreatedAt(resultSet.getDate("created_at").toLocalDate()); // Convert SQL Date to LocalDate
                    post.setUpdatedAt(resultSet.getDate("update_at").toLocalDate()); // Convert SQL Date to LocalDate
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding post by ID: " + e.getMessage());
        }
        return post;
    }

    @Override
    public ArrayList<Post> getByAttribute(String attributeName, String value) {
        ArrayList<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM `post` WHERE " + attributeName + "=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post();
                    post.setId(resultSet.getInt("id"));
                    post.setTitle(resultSet.getString("title"));
                    post.setContent(resultSet.getString("content"));
                    post.setImage(resultSet.getString("image"));
                    post.setCreatedAt(resultSet.getDate("created_at").toLocalDate()); // Convert SQL Date to LocalDate
                    post.setUpdatedAt(resultSet.getDate("update_at").toLocalDate()); // Convert SQL Date to LocalDate

                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting posts by attribute: " + e.getMessage());
        }
        return posts;
    }
}
