package uz.limon.chatsecurity.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "userSession", timeToLive = 60 * 60 * 24 * 10000)
public class UserSession {

    private String id;
    private String userInfo;
}
