package org.bfh.jass.game;

import org.bfh.jass.DatabaseManager;
import org.bfh.jass.user.User;

import java.sql.*;

/**
 * User: Simon
 * Date: 18.12.2014
 * Time: 10:29
 */
public class GameAccessor {
	private static GameAccessor instance = null;
	private final Connection conn;

	private GameAccessor() {
		conn = DatabaseManager.getConnection();
	}

	public static GameAccessor getInstance() {
		if (instance == null) instance = new GameAccessor();

		return instance;
	}


	public GameResult getGames(User user) {

		return null;
	}

	public void saveGame(Game game)  {
		GameResult result = game.getResult();
		try {
			try {
				int gameId;

				conn.setAutoCommit(false);
				PreparedStatement insertGameStatement = conn.prepareStatement("INSERT INTO games (`name`, `creatorId`, `requiredScore`) VALUES(?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);

				insertGameStatement.setString(1, game.getTitle());
				insertGameStatement.setInt(2, result.getCreator().getUserID());
				insertGameStatement.setInt(3, result.getRequiredScore());

				int affectedRows = insertGameStatement.executeUpdate();
				if (affectedRows == 0) throw new SQLException("Couldn't create game result.");

				try(ResultSet keys  = insertGameStatement.getGeneratedKeys()) {
					if(keys.next()) {
						gameId = keys.getInt(1);
					} else throw new SQLException("Couldn't create game result. (no id)");
				}
				System.out.println("id: " + gameId);

				PreparedStatement insertTeamStatement = conn.prepareStatement("INSERT INTO teams (`score`, `gameId`) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
				PreparedStatement insertTeamPlayer = conn.prepareStatement("INSERT INTO teams_users (`userId`, `teamId`) VALUES (?, ?)");

				for(GameResult.Team team: result.getTeams()) {
					int teamId;

					insertTeamStatement.setInt(1, team.getScore());
					insertTeamStatement.setInt(2, gameId);


					if(insertTeamStatement.executeUpdate() > 0) {

						try(ResultSet keys = insertTeamStatement.getGeneratedKeys()) {

							if(keys.next()) {
								teamId = keys.getInt(1);

							} else {
								throw new SQLException("Couldn't add team. (id)");
							}
						}
					} else throw new SQLException("Couldn't add team.");

					for(User user: team.getUsers()) {
						if(user != null) {
							insertTeamPlayer.setInt(1, user.getUserID());
							insertTeamPlayer.setInt(2, teamId);

							insertTeamPlayer.executeUpdate();
						}
					}
				}

				conn.commit();
				conn.setAutoCommit(true);

			} catch (SQLException e) {
				System.out.println("Rollback" + e.getMessage());

				conn.rollback();
				conn.setAutoCommit(true);
				throw e;
			}
		} catch(SQLException e) {
			System.out.println("Query went wrong: " + e.getMessage());
		}
		//conn.prepareStatement("INSERT INTO games");
	}
}
