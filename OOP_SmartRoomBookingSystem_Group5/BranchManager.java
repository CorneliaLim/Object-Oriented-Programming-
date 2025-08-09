import java.util.ArrayList;
import java.util.List;

public class BranchManager {
    private List<Branch> branches;

    public BranchManager() {
        this.branches = new ArrayList<>();
    }

    public void addBranch(Branch branch) {
        branches.add(branch);
    }

    public boolean deleteBranch(String branchName) {
        for (int i = 0; i < branches.size(); i++) {
            if (branches.get(i).getName().equals(branchName)) {
                branches.remove(i);
                return true;
            }
        }
        return false;
    }

    public Branch getBranchByName(String branchName) {
        for (Branch branch : branches) {
            if (branch.getName().equals(branchName)) {
                return branch;
            }
        }
        return null;
    }

    public List<Branch> getAllBranches() {
        return new ArrayList<>(branches);
    }

    public void displayAllBranches() {
        System.out.println("\n=== All Branches ===");
        for (int i = 0; i < branches.size(); i++) {
            System.out.println((i + 1) + ". " + branches.get(i).getName());
        }
    }

    public boolean hasBranch(String branchName) {
        return getBranchByName(branchName) != null;
    }
} 