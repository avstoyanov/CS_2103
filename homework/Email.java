/*
 * Back in the Dark Ages (before SnapChat, TikTok, Meta, etc.), people used
 * to write "emails" to each other. One person would write the first email in
 * the "thread" to another person; then the second person would respond back
 * to the first; then the first person might respond back to the second; etc.
 *
 * One way of modeling this is to create a class called Email that contains
 * an instance variable -- also of type Email -- that contains the *previous*
 * email in the thread, i.e., the one being replied to. This yields a
 * recursive data structure called a linked list.
 *
 * Tasks:
 *
 * 1. Create a class called Email with 2 private instance variables: one (of
 * type String) that contains the "contents" of the email (for simplicity,
 * we'll assume that this contains the text that was written, the sender and
 * recipient, any attachments, etc.), and another (of type Email) that stores
 * the *previous* email in the thread (i.e., the one that is being replied to).
 * The rule is that, if the current Email is the first one in the thread, than
 * the "previous" email instance variable should be null. Create a public
 * constructor that takes the String contents and Email previous as parameters
 * and initializes the private instance variables accordingly.
 *
 * 2. Create two methods -- getThreadLength, and
 * getThreadLengthRecursive -- that return the number of emails that exist in
 * the thread *including* the current email and all the previous emails, but
 * *not* including any future emails in the thread. As an example, consider
 * the following thread:
 *
 * d -> c -> b -> a
 *
 * Here, a is the first email in the thread, and d is the last email in the
 * thread. If getThreadLength (or getThreadLengthRecursive) is called on a,
 * then the method should return 1; if either method is called on c, it should
 * return 3; etc. Your getThreadLengthRecursive method should use recursion,
 * whereas getThreadLength should not (instead, use a loop to iterate over the
 * "previous" instance variables in the chain).
 *
 * 3. In your class' "main" method, instantiate a "thread" of 4 Email objects;
 * for the contents of each email, you can pass in any String (it doesn't
 * matter for this exercise). Call getThreadLength and
 * getThreadLengthRecursive on the last Email in the thread and verify that
 * both methods return 4. Call each method on the first Email in the thread
 * and verify that both methods return 1.
 *
 * 4. Also, in "main", instantiate a thread of 100 emails. Call the two
 * methods on the last Email and verify that they both return 100. (Note: in
 * Java, in contrast to Racket, recursion is not efficient if the recursion
 * depth is large. Hence, if you try this exercise on a thread of length, say,
 * 1,000,000, you, will get a stack overflow error.)
 */
public class Email {
	private String _contents;
	private Email _previous;

	public int getThreadLength(){
		int l = 1;
		Email e = _previous;
		while (e != null){
			l++;
			e=e._previous;
		}
		return l;
	}

	public int getThreadLengthRecursive(int l){
		if (_previous != null) return _previous.getThreadLengthRecursive(l+1);
		else return l+1;
	}

	public Email(String c, Email p){
		_contents = c;
		_previous = p;
	}

	public Email(){
		_contents = "";
	}

	public static void main (String[] args) {
		Email e1 = new Email();
		Email e2 = new Email("", e1);
		Email e3 = new Email("", e2);
		Email e4 = new Email("", e3);
		System.out.println(e4.getThreadLengthRecursive(0));
		System.out.println(e4.getThreadLength());
		System.out.println(e1.getThreadLengthRecursive(0));
		System.out.println(e1.getThreadLength());
		Email[] chain = new Email[100];
		chain[0] = new Email();
		for(int i=1; i<100; i++){
			chain[i] = new Email("", chain[i-1]);
		}
		System.out.println(chain[99].getThreadLengthRecursive(0));
		System.out.println(chain[99].getThreadLength());
	}
}
