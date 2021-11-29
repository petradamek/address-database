package cz.muni.fi.pv168.addresses.loader;

import cz.muni.fi.pv168.addresses.loader.mvcr.MvcrDataLoader;

import java.nio.file.Paths;

public class DataLoaderFactory {

    public static DataLoader newDataLoader() {
        return new MvcrDataLoader(Paths.get("adresy.zip"));
    }

}
