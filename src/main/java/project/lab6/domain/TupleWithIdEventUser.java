package project.lab6.domain;

import java.util.Objects;

public class TupleWithIdEventUser {

    public final Long idEvent;
    public final Long idUser;

    public TupleWithIdEventUser(Long idEvent, Long idUser) {
        this.idEvent = idEvent;
        this.idUser = idUser;
    }

    public Long getIdEvent() {
        return idEvent;
    }

    public Long getIdUser() {
        return idUser;
    }

    @Override
    public String toString() {
        return "TupleWithIdEventUser{" +
                "idEvent=" + idEvent +
                ", idUser=" + idUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TupleWithIdEventUser that = (TupleWithIdEventUser) o;
        return idEvent.equals(that.idEvent) && idUser.equals(that.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvent, idUser);
    }


}
