
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class PathFindingControllerTest {
/*

    @Test
    //Larry - Test for finding path form Conference room to Department
    public void findPathTest2() {

        PathFindingController A = new PathFindingController();
        Singleton single = Singleton.getInstance();
        single.setNum(0);

        A.initialize();

        Location start = A.getLookup().get("DCONF00102");
        Location end = A.getLookup().get("DDEPT00202");

        AStarStrategy astar = new AStarStrategy(A.getLookup());
        Path a = A.findAbstractPath(astar,start,end);
        ArrayList<Location> c = new ArrayList<Location>();
        Path b = new Path(c);
        Location q = A.getLookup().get("DHALL02702");
        Location w = A.getLookup().get("DHALL02602");
        Location e = A.getLookup().get("DHALL01902");
        Location r = A.getLookup().get("DHALL01702");
        Location t = A.getLookup().get("DHALL01602");
        Location y = A.getLookup().get("DHALL01202");



        b.addToPath(end);
        b.addToPath(q);
        b.addToPath(w);
        b.addToPath(e);
        b.addToPath(r);
        b.addToPath(t);
        b.addToPath(y);


        assertEquals(b.toString(),a.toString());

    }

    @Test
    //Larry - Test for finding path from STAIR to HALLWAY
    public void findPathTest3() {

        PathFindingController A = new PathFindingController();
        Singleton single = Singleton.getInstance();
        single.setNum(0);

        A.initialize();
        Location start = A.getLookup().get("DSTAI00302");
        Location end = A.getLookup().get("DHALL04502");


        AStarStrategy astar = new AStarStrategy(A.getLookup());
        Path a = A.findAbstractPath(astar,start,end);
        ArrayList<Location> c = new ArrayList<Location>();
        Path b = new Path(c);
        Location q = A.getLookup().get("DHALL04402");
        Location w = A.getLookup().get("DHALL04302");
        Location e = A.getLookup().get("DHALL03402");
        Location r = A.getLookup().get("DHALL03202");
        Location t = A.getLookup().get("DHALL03002");
        Location y = A.getLookup().get("DHALL02902");
        Location u = A.getLookup().get("DHALL02802");
        Location i = A.getLookup().get("DHALL02602");
        Location o = A.getLookup().get("DHALL01902");
        Location p = A.getLookup().get("DHALL06002");
        Location z = A.getLookup().get("DHALL02002");
        Location x = A.getLookup().get("DHALL02102");
        Location v = A.getLookup().get("DHALL02202");
        Location n = A.getLookup().get("DHALL02302");




        b.addToPath(end);
        b.addToPath(q);
        b.addToPath(w);
        b.addToPath(e);
        b.addToPath(r);
        b.addToPath(t);
        b.addToPath(y);
        b.addToPath(u);
        b.addToPath(i);
        b.addToPath(o);
        b.addToPath(p);
        b.addToPath(z);
        b.addToPath(x);
        b.addToPath(v);
        b.addToPath(n);


        assertEquals(b.toString(),a.toString());

    }

    @Test
    //Larry - Test for finding path from Lab to Restroom
    public void findPathTest4() {

        PathFindingController A = new PathFindingController();
        Singleton single = Singleton.getInstance();
        single.setNum(0);

        A.initialize();
        Location start = A.getLookup().get("DLABS00102");
        Location end = A.getLookup().get("DREST00302");


        AStarStrategy astar = new AStarStrategy(A.getLookup());
        Path a = A.findAbstractPath(astar,start,end);
        ArrayList<Location> c = new ArrayList<Location>();
        Path b = new Path(c);
        Location q = A.getLookup().get("DHALL05002");
        Location w = A.getLookup().get("DHALL04802");
        Location e = A.getLookup().get("DHALL04702");
        Location r = A.getLookup().get("DHALL04602");

        b.addToPath(end);
        b.addToPath(q);
        b.addToPath(w);
        b.addToPath(e);
        b.addToPath(r);


        assertEquals(b.toString(),a.toString());

    }

    @Test
    //Larry - Test for finding path from Security desk to Brigham Health department
    public void findPathTest5() {

        PathFindingController A = new PathFindingController();
        Singleton single = Singleton.getInstance();
        single.setNum(0);

        A.initialize();
        Location start = A.getLookup().get("DINFO00202");
        Location end = A.getLookup().get("DDEPT00302");


        AStarStrategy astar = new AStarStrategy(A.getLookup());
        Path a = A.findAbstractPath(astar,start,end);
        ArrayList<Location> c = new ArrayList<Location>();
        Path b = new Path(c);
        Location q = A.getLookup().get("DHALL04002");
        Location w = A.getLookup().get("DHALL03402");
        Location e = A.getLookup().get("DHALL04302");
        Location r = A.getLookup().get("DHALL04602");
        Location t = A.getLookup().get("DHALL04702");
        Location y = A.getLookup().get("DHALL04802");
        Location u = A.getLookup().get("DHALL05002");
        Location i = A.getLookup().get("DHALL05102");
        Location o = A.getLookup().get("DHALL05302");
        Location p = A.getLookup().get("DHALL05402");

        b.addToPath(end);
        b.addToPath(q);
        b.addToPath(w);
        b.addToPath(e);
        b.addToPath(r);
        b.addToPath(t);
        b.addToPath(y);
        b.addToPath(u);
        b.addToPath(i);
        b.addToPath(o);
        b.addToPath(p);


        assertEquals(b.toString(),a.toString());

    }

    @Test
    //Larry - Test for finding path from Elevator to Conference room
    public void findPathTest6() {

        PathFindingController A = new PathFindingController();
        Singleton single = Singleton.getInstance();
        single.setNum(0);

        A.initialize();
        Location start = A.getLookup().get("DELEV00A02");
        Location end = A.getLookup().get("DCONF00102");


        AStarStrategy astar = new AStarStrategy(A.getLookup());
        Path a = A.findAbstractPath(astar,start,end);
        ArrayList<Location> c = new ArrayList<Location>();
        Path b = new Path(c);

        Location r = A.getLookup().get("DHALL01202");
        Location q = A.getLookup().get("DHALL01302");
        Location w = A.getLookup().get("DHALL01402");
        Location e = A.getLookup().get("DHALL01502");

        b.addToPath(end);
        b.addToPath(r);
        b.addToPath(q);
        b.addToPath(w);
        b.addToPath(e);


        assertEquals(b.toString(),a.toString());

    }

    @Test
    //Larry - Test for finding path that is Back way of (Elevator to Conf)
    public void findPathTest7() {

        PathFindingController A = new PathFindingController();
        Singleton single = Singleton.getInstance();
        single.setNum(0);

        A.initialize();
        Location start = A.getLookup().get("DCONF00102");
        Location end = A.getLookup().get("DELEV00A02");


        AStarStrategy astar = new AStarStrategy(A.getLookup());
        Path a = A.findAbstractPath(astar,start,end);
        ArrayList<Location> c = new ArrayList<Location>();
        Path b = new Path(c);

        Location r = A.getLookup().get("DHALL01502");
        Location q = A.getLookup().get("DHALL01402");
        Location w = A.getLookup().get("DHALL01302");
        Location e = A.getLookup().get("DHALL01202");

        b.addToPath(end);
        b.addToPath(r);
        b.addToPath(q);
        b.addToPath(w);
        b.addToPath(e);


        assertEquals(b.toString(),a.toString());

    }

    @Test
    //Larry - Test for finding path from EXIT to HALLWAY
    public void findPathTest8() {

        PathFindingController A = new PathFindingController();
        Singleton single = Singleton.getInstance();
        single.setNum(0);

        A.initialize();
        Location start = A.getLookup().get("DEXIT00102");
        Location end = A.getLookup().get("DHALL06002");


        AStarStrategy astar = new AStarStrategy(A.getLookup());
        Path a = A.findAbstractPath(astar,start,end);
        ArrayList<Location> c = new ArrayList<Location>();
        Path b = new Path(c);

        Location r = A.getLookup().get("DHALL01902");
        Location q = A.getLookup().get("DHALL02602");
        Location w = A.getLookup().get("DHALL02702");


        b.addToPath(end);
        b.addToPath(r);
        b.addToPath(q);
        b.addToPath(w);


        assertEquals(b.toString(),a.toString());

    }

    @Test
    //Larry - Test for finding path between two neighbor location
    public void findPathTest9() {

        PathFindingController A = new PathFindingController();
        Singleton single = Singleton.getInstance();
        single.setNum(0);

        A.initialize();
        Location start = A.getLookup().get("DHALL04602");
        Location end = A.getLookup().get("DHALL04702");


        AStarStrategy astar = new AStarStrategy(A.getLookup());
        Path a = A.findAbstractPath(astar,start,end);
        ArrayList<Location> c = new ArrayList<Location>();
        Path b = new Path(c);


        b.addToPath(end);

        assertEquals(b.toString(),a.toString());

    }

 */
}