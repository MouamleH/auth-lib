package tech.enjaz.mouamle.authlib.session;

import java.util.Objects;

public class SessionData<D> {

    private D data;

    public SessionData(D data) {
        this.data = data;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionData<?> that = (SessionData<?>) o;
        return Objects.equals(this.data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return "SessionData{" +
                "data=" + data +
                '}';
    }

}
