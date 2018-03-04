
class pageNode {
	public pageNode next;
	public TLBEntry data;

	public pageNode(TLBEntry data, pageNode next) {
		this.next = next;
		this.data = data;
	}
}

public class Clock {

	private pageNode head;
	private pageNode tail;
	protected pageNode hand;
	private int pageNodeNum;

	public Clock(){
		head = null;
		tail = null;
		hand = head;
		pageNodeNum = 0;
	}

	public boolean isEmpty() {
		return (pageNodeNum == 0);
	}
	
	public int getNum(){
		return pageNodeNum;
	}

	public void insertFirst(TLBEntry data) {
		pageNode pageNode = new pageNode(data, null);
		pageNode.next = head;
		if (head == null) {
			head = pageNode;
			pageNode.next = head;
			hand = head;
			tail = head;
		}
		else if(hand.data == null){
			hand.data = data;
		}
		else{
			tail.next = pageNode;
			head = pageNode;
		}
		
		pageNodeNum++;
	}

	public void insertNext(TLBEntry data) {
		pageNode pageNode = new pageNode(data, null);
		pageNode.next = head;
		if (head == null) {
			head = pageNode;
			pageNode.next = head;
			hand = head;
			tail = head;
		}
		else if(hand.data == null){
			hand.data = data;
		}
		else{
			tail.next = pageNode;
			tail = pageNode;
		}
		
		pageNodeNum++;
	}
	
	public void display()
    {
        System.out.print("\nCircular Singly Linked List = ");
        pageNode ptr = head;
        if (pageNodeNum == 0) 
        {
            System.out.print("empty\n");
            return;
        }
        if (head.next == head) 
        {
            System.out.print(head.data.getPageFrameNum() + "->"+ptr.data.getPageFrameNum()+ "\n");
            return;
        }
        System.out.print(head.data.getPageFrameNum() + "->");
        ptr = head.next;
        while (ptr.next != head) 
        {
            System.out.print(ptr.data.getPageFrameNum()+ "->");
            ptr = ptr.next;
        }
        System.out.print(ptr.data.getPageFrameNum()+ "->");
        ptr = ptr.next;
        System.out.print(ptr.data.getPageFrameNum()+ "\n");
    }

	public TLBEntry getHandData() {
		return hand.data;
	}
}
