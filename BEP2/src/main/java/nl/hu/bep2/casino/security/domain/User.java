package nl.hu.bep2.casino.security.domain;

import nl.hu.bep2.casino.blackjack.game.application.exception.NoGameFoundException;
import nl.hu.bep2.casino.blackjack.game.domain.Game;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false, unique = true)
    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    @OneToOne(cascade = CascadeType.ALL)
    private Game currentGame;

    public User() {
    }

    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Game getCurrentGame() {
        if(currentGame != null){
            return currentGame;
        }else throw new NoGameFoundException();
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public void removeCurrentGame(){
        this.setCurrentGame(null);
    }
}