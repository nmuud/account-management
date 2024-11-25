package com.example.account.repository;

import com.example.account.domain.Account;
import com.example.account.domain.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findFirstByOrderByIdDesc();

    Integer countByAccountUser(AccountUser accountUser);

    Optional<Account> findByAccountNumber(String AccountNumber);

    // 수정 전: 삭제된 계좌도 조회가 되는 문제가 있음
    // 사용자가 소유한 모든 계좌를 조회(사용 중인 계좌 + 삭제된 계좌)
    //List<Account> findByAccountUser(AccountUser accountUser);

    /**
     * 사용자가 소유한 계좌 중 현재 사용 중인 계좌(IN_USE 상태)만 조회합니다
     *
     * @param accountUser 계좌 소유자 (AccountUser 객체)
     * @return 사용 중인 계좌 리스트 (IN_USE 상태)
     *
     * 수정 이유:
     * - 기존 메서드(List<Account> findByAccountUser)는 삭제된 계좌도 반환되는 문제가 있었습니다
     * - 계좌 상태(AccountStatus)를 필터링하여 IN_USE 상태의 계좌만 반환합니다
     */
    @Query("SELECT a FROM Account a WHERE a.accountUser = :accountUser AND a.accountStatus = 'IN_USE'")
    List<Account> findByAccountUser(@Param("accountUser") AccountUser accountUser);
}
