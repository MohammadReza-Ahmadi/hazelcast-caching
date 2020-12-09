package com.vosouq.hazelcast.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;

@Data
@ToString
//@Getter
//@Setter
//@EqualsAndHashCode
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fico_scoring")
//public class FicoScoring implements DataSerializable {
public class FicoScoring implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
    private Float paymentHistory;
    private Float amountsOwed;
    private Float lengthOfCreditHistory;
    private Float newCredit;
    private Float creditMix;
    private int score;

/*    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeLong(id);
//        if (person == null)
            person.writeData(out);
        out.writeFloat(paymentHistory);
        out.writeFloat(amountsOwed);
        out.writeFloat(lengthOfCreditHistory);
        out.writeFloat(newCredit);
        out.writeFloat(creditMix);
        out.writeInt(score);
    }*/

/*    @Override
    public void readData(ObjectDataInput in) throws IOException {
        id = in.readLong();
//        if(person==null) {
            person = new Person();
            person.readData(in);
//        }
        paymentHistory = in.readFloat();
        amountsOwed = in.readFloat();
        lengthOfCreditHistory = in.readFloat();
        newCredit = in.readFloat();
        creditMix = in.readFloat();
        score = in.readInt();
    }*/

    @Override
    public String toString(){
        return "ficoScoring";
    }


}
