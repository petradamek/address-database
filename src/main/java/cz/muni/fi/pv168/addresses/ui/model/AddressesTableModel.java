package cz.muni.fi.pv168.addresses.ui.model;

import cz.muni.fi.pv168.addresses.model.Address;

import java.util.List;

public class AddressesTableModel extends ReadOnlyEntityTableModel<Address> {

    private static final List<Column<Address, ?>> COLUMNS = List.of(
//            Column.readOnly("Full Address", String.class, Address::toString),
            Column.readOnly("Street", String.class, Address::getStreet),
            Column.readOnly("Orientation Number", String.class, Address::getOrientationNo),
            Column.readOnly("House Number Type", Address.HouseNoType.class, Address::getHouseNoType),
            Column.readOnly("House Number", Integer.class, Address::getHouseNo),
            Column.readOnly("Number", String.class, Address::getOrientationNo),
            Column.readOnly("Municipality District", String.class, Address::getMunicipalDistrict),
            Column.readOnly("Municipality", String.class, Address::getMunicipality),
            Column.readOnly("District", String.class, Address::getDistrict)
//            Column.readOnly("Post Code", String.class, Address::getPostCode),
//            Column.readOnly("Post", String.class, Address::getPost),
    );

    public AddressesTableModel() {
        super(COLUMNS);
    }
}
