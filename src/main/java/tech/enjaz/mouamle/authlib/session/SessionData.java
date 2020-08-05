package tech.enjaz.mouamle.authlib.session;

import java.util.Objects;

public class SessionData<D> {

    private final D data;
    private String role;

    public SessionData(D data) {
        this.data = data;
    }

    public SessionData(D data, String role) {
        this.data = data;
        this.role = role;
    }

    public D getData() {
        return data;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionData<?> that = (SessionData<?>) o;
        return Objects.equals(data, that.data) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, role);
    }

    @Override
    public String toString() {
        return "SessionData{" +
                "data=" + data +
                ", role='" + role + '\'' +
                '}';
    }

}
