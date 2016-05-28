package au.edu.unsw.soacourse.loanapprover;

import javax.jws.WebService;

import au.edu.unsw.soacourse.loandefinitions.ObjectFactory;
import au.edu.unsw.soacourse.loandefinitions.ApprovalType;
import au.edu.unsw.soacourse.loandefinitions.LoanInputType;

@WebService(endpointInterface = "au.edu.unsw.soacourse.loanapprover.LoanApprovalPT")
public class LoanApprovalPTImpl implements LoanApprovalPT {

	@Override
	public ApprovalType approve(LoanInputType loanreq) {
		ObjectFactory factory = new ObjectFactory();
		ApprovalType res = factory.createApprovalType();
		String license = loanreq.getFirstName();
		String address = loanreq.getName();
		if (license.length() < 3 && address.length() < 3) {
			res.setAccept("no");
		} else {
			res.setAccept("yes");
		}
		return res;
	}

}
