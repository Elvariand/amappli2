package isika.p3.amappli.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {
    
    private Map<String, Object> additionalInfo;

    public CustomUserDetails(String username, String password,
                             Collection<? extends GrantedAuthority> authorities,
                             Map<String, Object> additionalInfo) {
        super(username, password, authorities);
        this.additionalInfo = additionalInfo != null ? additionalInfo : new HashMap<>();
    }

    public Map<String, Object> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionInfo(Map<String, Object> info){
        this.additionalInfo = info;
    }

    // Method to add a single key-value piece of information
    public void addAdditionalInfo(String key, Object value) {
        if (this.additionalInfo == null) {
            this.additionalInfo = new HashMap<>();
        }
        this.additionalInfo.put(key, value);
    }

     // Method to get a single piece of additional info by giving the key
     public Object getAdditionalInfoByKey(String key) {
        return this.additionalInfo.get(key); // Returns the value for the given key, or null if not found
    }

    // Method to add multiple key-value pairs of information at once
    public void addAdditionalInfo(Map<String, Object> additionalInfo) {
        if (this.additionalInfo == null) {
            this.additionalInfo = new HashMap<>();
        }
        this.additionalInfo.putAll(additionalInfo);
    }

    // Using Builder pattern to match the default userdetails.User class synthax
    public static class Builder {

        private String username;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;
        private Map<String, Object> additionalInfo = new HashMap<>();

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public Builder additionalInfo(Map<String, Object> additionalInfo) {
            this.additionalInfo = additionalInfo;
            return this;
        }

        public Builder addAdditionalInfo(String key, Object value) {
            if (this.additionalInfo == null) {
                this.additionalInfo = new HashMap<>();
            }
            this.additionalInfo.put(key, value);
            return this;
        }

        // Build method to construct CustomUserDetails instance
        public CustomUserDetails build() {
            return new CustomUserDetails(username, password, authorities, additionalInfo);
        }
    }
}
