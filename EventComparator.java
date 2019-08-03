package cs2030.simulator;

import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        Customer c1 = e1.getCustomer();
        Customer c2 = e2.getCustomer();

        double time1 = e1.getTime();
        double time2 = e2.getTime();

        int id1 = c1.getID();
        int id2 = c2.getID();

        int state1 = e1.getState();
        int state2 = e2.getState();

        if (time1 < time2) {
            return -1;
        } else if (time1 == time2) {
            if (id1 < id2) {
                return -1;
            } else if (id1 > id2) {
                return 1;
            } else {
                if (state1 < state2) {
                    return -1;
                } else if (state1 > state2) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } else if (time1 > time2) {
            return 1;
        } else {
            return 0;
        }
    }
}
