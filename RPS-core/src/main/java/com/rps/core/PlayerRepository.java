package com.rps.core;

import java.util.List;

public interface PlayerRepository {
    List<Player> findAll();
    Player findById( int id );
    Player save( Player player );
}
