package edu.miu.PropertyManagement.repository;

import edu.miu.PropertyManagement.entity.Message;
import edu.miu.PropertyManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository  extends JpaRepository<Message,Long> {
    List<Message> findByRecipientId(Long recipientId);
    List<Message> findBySenderId(Long senderId);
    List<Message> findBySenderAndRecipient(User sender, User recipient);
}
