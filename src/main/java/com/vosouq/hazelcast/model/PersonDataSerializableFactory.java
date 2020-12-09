package com.vosouq.hazelcast.model;

import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

/**
 * author: Mohammad Reza Ahmadi
 * email: m.reza79ahmadi@gmail.com
 */

public class PersonDataSerializableFactory implements DataSerializableFactory {

    public static final int FACTORY_ID = 1;

    public static final int EMPLOYEE_TYPE = 1;

    @Override
    public IdentifiedDataSerializable create(int typeId) {
/*        if ( typeId == EMPLOYEE_TYPE ) {
            return new Person();
        } else {
            return null;
        }*/
        return null;
    }
}
