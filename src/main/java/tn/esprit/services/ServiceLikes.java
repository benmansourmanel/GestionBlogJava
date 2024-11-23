package tn.esprit.services;

import tn.esprit.interfaces.IServiceLikes;
import tn.esprit.models.Likes;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;

public class ServiceLikes implements IServiceLikes<Likes> {
    private Connection cnx;

    public ServiceLikes() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Likes like) {
        String query = "INSERT INTO `likes`(`id_post`, `user_id`, `is_liked`) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, like.getPostId());
            preparedStatement.setInt(2, like.getUserId());
            preparedStatement.setBoolean(3, like.isLiked());

            preparedStatement.executeUpdate();
            System.out.println("Like added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding like: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Likes> getAll() {
        ArrayList<Likes> likesList = new ArrayList<>();
        String query = "SELECT * FROM `likes`";
        try (Statement statement = cnx.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Likes like = new Likes();
                like.setId(resultSet.getInt("id"));
                like.setPostId(resultSet.getInt("id_post"));
                like.setUserId(resultSet.getInt("user_id"));
                like.setLiked(resultSet.getBoolean("is_liked"));

                likesList.add(like);
            }
        } catch (SQLException e) {
            System.out.println("Error getting likes: " + e.getMessage());
        }
        return likesList;
    }

    @Override
    public void update(Likes like) {
        String query = "UPDATE `likes` SET id_post=?, user_id=?, is_liked=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, like.getPostId());
            preparedStatement.setInt(2, like.getUserId());
            preparedStatement.setBoolean(3, like.isLiked());
            preparedStatement.setInt(4, like.getId());

            preparedStatement.executeUpdate();
            System.out.println("Like updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating like: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Likes like) {
        String query = "DELETE FROM `likes` WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, like.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Like deleted successfully.");
                return true;
            } else {
                System.out.println("No like found with the given ID.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting like: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Likes findById(int id) {
        Likes like = null;
        String query = "SELECT * FROM `likes` WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    like = new Likes();
                    like.setId(resultSet.getInt("id"));
                    like.setPostId(resultSet.getInt("id_post"));
                    like.setUserId(resultSet.getInt("user_id"));
                    like.setLiked(resultSet.getBoolean("is_liked"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding like by ID: " + e.getMessage());
        }
        return like;
    }

    @Override
    public ArrayList<Likes> getByPostId(int postId) {
        ArrayList<Likes> likesList = new ArrayList<>();
        String query = "SELECT * FROM `likes` WHERE id_post=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, postId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Likes like = new Likes();
                    like.setId(resultSet.getInt("id"));
                    like.setPostId(resultSet.getInt("id_post"));
                    like.setUserId(resultSet.getInt("user_id"));
                    like.setLiked(resultSet.getBoolean("is_liked"));

                    likesList.add(like);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting likes by post ID: " + e.getMessage());
        }
        return likesList;
    }

    @Override
    public ArrayList<Likes> getByUserId(int userId) {
        ArrayList<Likes> likesList = new ArrayList<>();
        String query = "SELECT * FROM `likes` WHERE user_id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Likes like = new Likes();
                    like.setId(resultSet.getInt("id"));
                    like.setPostId(resultSet.getInt("id_post"));
                    like.setUserId(resultSet.getInt("user_id"));
                    like.setLiked(resultSet.getBoolean("is_liked"));

                    likesList.add(like);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting likes by user ID: " + e.getMessage());
        }
        return likesList;
    }
}
