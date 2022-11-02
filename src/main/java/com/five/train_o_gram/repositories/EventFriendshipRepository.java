package com.five.train_o_gram.repositories;

import com.five.train_o_gram.models.EventFriendship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventFriendshipRepository extends JpaRepository<EventFriendship, Integer> {
}