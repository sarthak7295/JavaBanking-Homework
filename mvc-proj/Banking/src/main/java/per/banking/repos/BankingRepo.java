package per.banking.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import per.banking.entity.Customer;

import java.util.List;

@Repository
@Transactional
public interface BankingRepo extends JpaRepository<Customer, String> {


    List<Customer> findByUserName(String text);

}
