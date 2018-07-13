package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.entites.Order;

public interface Orderrepository extends JpaRepository<Order, Long> {

}
