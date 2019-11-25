package ca.ubc.cs304.model;

public class RentalReturn {
    private Rental rental;
    private Branch branch;

    public RentalReturn(Rental rental, Branch branch) {
        this.rental = rental;
        this.branch = branch;
    }

    public Branch getBranch() {
        return branch;
    }

    public Rental getRental() {
        return rental;
    }
}
