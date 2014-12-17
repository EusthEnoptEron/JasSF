package org.bfh.jass.game;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.SetUtils;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@RequestScoped
public class GameListBean implements Serializable {

	public GameListBean() {
	}

	public Game[] getGames() {
		return GameManager.getInstance().getOpenGames();
	}
	public boolean hasGames() {
		return getGames().length > 0;
	}
}
