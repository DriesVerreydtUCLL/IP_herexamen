package db;

import domain.Player;
import exception.DatabaseException;
import exception.DomainException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dries
 */
public class DatabaseInMemory extends PlayerDatabase {
    
    List<Player> players;
    private Long id = Long.MIN_VALUE;
    
    public DatabaseInMemory() throws DatabaseException {
        players = new ArrayList<>();
        
        try{
            Player hazard = new Player("Eden","Hazard",10,5);
            Player debruyne = new Player("Kevin","De Bruyne",7,3);
            Player nainggolan = new Player("Radja","Nainggolan",4,1);
            Player vertonghen = new Player("Jan","Vertonghen",5,1);
            Player courtois = new Player("Thibaut","Courtois",1,0);
            
            players.add(hazard);
            players.add(debruyne);
            players.add(nainggolan);
            players.add(vertonghen);
            players.add(courtois);
            
        } catch (DomainException e){
            throw new DatabaseException("Error while setting up in memory db.",e);
        }
                
    }
    @Override
    public List<Player> getPlayers() throws DatabaseException {
        List<Player> all = new ArrayList<>();
        for(Player p : players){
            all.add(p);
        }
        return all;
    }

    @Override
    public Player getPlayer(Long id) throws DatabaseException {
        for(Player p : players){
            if(p.getId().equals(id)){
                return p;
            }
        }
        throw new DatabaseException("Player with id " + id + " not found.");
    }

    @Override
    public void addPlayer(Player player) throws DatabaseException {
        if(contains(player.getId())){
            throw new DatabaseException("A player with " + player.getId() + " is already present in the database.");
        } else {
            player.setId(id);
            id++;
            players.add(player);
        }
    }

    @Override
    public void removePlayer(Long id) throws DatabaseException {
        if(contains(id)){
            Player p = getPlayer(id);
            players.remove(p);
        } else {
            throw new DatabaseException("A player with " + id + " is not present in the database.");
        }
    }

    @Override
    public void updatePlayer(Player player) throws DatabaseException {
        if(contains(player.getId())){
            players.remove(player);
            players.add(player);
        } else {
            throw new DatabaseException("A player with " + player.getId() + " is not present in the database.");
        }
    }
    
    private boolean contains(Long id){
        boolean contains = false;
        for(Player p : players){
            if(p.getId().equals(id)){
                contains = true;
            }
        }
        return contains;
    }
}
