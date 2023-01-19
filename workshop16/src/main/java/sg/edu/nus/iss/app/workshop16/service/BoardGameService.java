package sg.edu.nus.iss.app.workshop16.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.workshop16.model.Mastermind;

@Service
public class BoardGameService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public int saveGame(final Mastermind mds) {
        System.out.println("mds " + mds.toJSON().toString());
        redisTemplate.opsForValue().set(mds.getId(), mds.toJSON().toString());
        String result = (String) redisTemplate.opsForValue().get(mds.getId());
        if (result != null)
            return 1;
        return 0;
    }

    public Mastermind findById(final String msid) throws IOException {
        String mdsStr = (String) redisTemplate.opsForValue().get(msid);
        Mastermind m = Mastermind.create(mdsStr);
        return m;
    }

    public int update(final Mastermind mds) {
        System.out.println("upsert ? " + mds.isUpSert());
        if (mds.isUpSert()) {
            System.out.println("upsert is true");
            redisTemplate.opsForValue().set(mds.getId(), mds.toJSON().toString());
        } else {
            System.out.println("upsert is false");
            redisTemplate.opsForValue().setIfAbsent(mds.getId(), mds.toJSON().toString());
        }
        String result = (String) redisTemplate.opsForValue()
                .get(mds.getId());

        if (null != result)
            return 1;
        return 0;
    }

}
