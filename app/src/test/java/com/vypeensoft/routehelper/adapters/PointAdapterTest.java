package com.vypeensoft.routehelper.adapters;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.location.Location;
import com.vypeensoft.routehelper.models.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class PointAdapterTest {

    private PointAdapter adapter;
    private List<Point> points;
    private PointAdapter.OnPointClickListener mockListener;

    @Before
    public void setUp() {
        System.out.println("Test Setup is running ...");
        points = new ArrayList<>();

        // Create some sample points
        points.add(new Point("House"                  , 12.9459565,  77.7098682, "t1", null));
        points.add(new Point("Fine Mart"              , 12.9521856,  77.70618  , "t2", null));
        points.add(new Point("Grand Mini Bazaar"      , 12.9501816,  77.7069595, "t3", null));
        points.add(new Point("Sargam Sweets"          , 12.9487859,  77.7081853, "t4", null));
        points.add(new Point("Chirayu"                , 12.9508012,  77.7066536, "t5", null));
        points.add(new Point("Loyal World"            , 12.9595073,  77.7484656, "t6", null));
        points.add(new Point("Ittina Abha"            , 12.9536261,  77.7055137, "t7", null));
        points.add(new Point("Bridge"                 , 12.956009,   77.7048589, "t8", null));
        points.add(new Point("Lakshmi Narayana Temple", 12.9478334,  77.709014 , "t9", null));

        
        mockListener = mock(PointAdapter.OnPointClickListener.class);
        adapter = new PointAdapter(points, mockListener);
    }

    @Test
    public void testUpdateCurrentLocation_MovesUserRow() {
        //System.out.println("Test Case: testUpdateCurrentLocation_MovesUserRow is running ...");
        // 1. Initial State
		
		Location startLoc = null;

        startLoc = createLocation(12.956812,77.707098);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.956868,77.706406);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.956923,77.706205);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.956967,77.706284);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.956969,77.706102);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.956909,77.705387);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.956908,77.705367);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.956865,77.705235);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.956855,77.705215);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.956852,77.705215);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.956849,77.705215);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.956643,77.705183);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.956294,77.705128);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.955700,77.704990);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.955404,77.705042);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.955286,77.704985);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.954992,77.704769);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.954470,77.705099);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.954415,77.705140);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.954202,77.705208);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.953640,77.705410);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.953368,77.705450);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.953356,77.705455);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.952907,77.705752);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.952539,77.705855);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.952539,77.705858);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.952529,77.705868);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.952531,77.705867);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.952532,77.705865);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.952384,77.706239);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.952158,77.706409);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.952100,77.706419);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.952092,77.706422);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.951039,77.706731);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.950983,77.706748);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.950986,77.706736);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.951046,77.706994);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.951096,77.707219);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.951085,77.707219);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.951100,77.707680);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.951114,77.707818);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.951115,77.707822);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.951111,77.707826);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.951108,77.708433);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.951109,77.708663);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.951080,77.708678);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.950774,77.708901);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.950612,77.709010);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.950502,77.709032);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.950513,77.709029);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.950512,77.709033);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.949796,77.709260);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.949559,77.709238);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.949109,77.709159);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.948894,77.709150);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.948736,77.709166);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.948343,77.709054);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.948052,77.709008);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947720,77.708982);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947516,77.708940);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947448,77.708945);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947464,77.708927);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947475,77.708931);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947480,77.708932);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947530,77.708949);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947511,77.708933);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947491,77.708931);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947487,77.708929);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947479,77.708922);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947480,77.708910);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947477,77.708914);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947479,77.708928);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947513,77.708941);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947493,77.708910);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947480,77.708931);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947381,77.708914);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.947202,77.708903);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.946967,77.708877);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.946779,77.708863);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.946745,77.708858);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.946743,77.708958);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.946709,77.709006);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.946628,77.709195);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.946594,77.709334);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.946517,77.709409);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.946431,77.709386);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.946316,77.709454);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945988,77.709795);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945949,77.709872);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945937,77.709865);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945833,77.709897);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945937,77.709865);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945929,77.709851);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945944,77.709835);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945958,77.709849);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945956,77.709862);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945953,77.709865);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945960,77.709866);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945941,77.709861);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945949,77.709863);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945952,77.709863);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945951,77.709860);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945946,77.709863);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945960,77.709861);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945953,77.709861);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945942,77.709858);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945950,77.709858);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945950,77.709864);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945952,77.709864);  adapter.updateCurrentLocation(startLoc);
        startLoc = createLocation(12.945948,77.709859);  adapter.updateCurrentLocation(startLoc);


//        Location startLoc = createLocation(0, 0);
//        adapter.updateCurrentLocation(startLoc);
//
//        // 2. Move to a new location
//        Location nextLoc = createLocation(5, 5);
//        adapter.updateCurrentLocation(nextLoc);

        // Verify that internal logic (sorting/reordering) happened
        // Since fields are private, you can verify behavior:
        // e.g., the userRowPosition should be valid
        assertTrue(adapter.getItemCount() > points.size());
    }

    private Location createLocation(double lat, double lon) {
        //System.out.println("Test Case: createLocation is running ...");
        Location loc = new Location("gps");
        loc.setLatitude(lat);
        loc.setLongitude(lon);
        loc.setTime(System.currentTimeMillis());
        return loc;
    }
}
