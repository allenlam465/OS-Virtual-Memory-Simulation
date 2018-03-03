
class pageNode {
    public pageNode next;
    public PageTableEntry data;

    public pageNode(PageTableEntry data, pageNode next) {
        this.next = next;
        this.data = data;
    }
}

public class Clock {
	
	private pageNode head = null;
	private pageNode hand = null;
    private int numberOfElements = 0;
    private pageNode nextNode = null;
    private int index = 0;

    public boolean isEmpty() {
        return (numberOfElements == 0);
    }
    
    public void insertFirst(PageTableEntry data) {
        if (!(isEmpty())) {
            index++;
        }
        pageNode pageNode = new pageNode(data, head);
        head = pageNode;
        hand = head;
        numberOfElements++;
    }
    
    public void insertNext(PageTableEntry data) {
        pageNode pageNode = new pageNode(data, nextNode.next);
        nextNode.next = pageNode;
        numberOfElements++;
    }
    

    public boolean deleteFirst() {
        if (isEmpty())
            return false;
        if (index > 0)
            index--;
        head= hand.next;
        numberOfElements--;
        return true;
    }
    
    public boolean deletenextNode() {
        if (index > 0) {
            numberOfElements--;
            index--;
            pageNode listNode = head;
            while (listNode.next.equals(nextNode) == false)
                listNode = listNode.next;
            listNode.next = nextNode.next;
            nextNode = listNode;
            return true;
        }
        else {
            nextNode = head.next;
            index = 0;
            return deleteFirst();
        }
    }

    
    
    public PageTableEntry evict(){
		while (true){
			if(hand.data.getReference().equals("0")){
				PageTableEntry output = hand.data;
				hand.data = null;
				return output;
			}
			else if(hand.data.getReference().equals("1")){
				hand.data.setReference("0");
				
		        index = (index + 1) % numberOfElements;
		        if (index == 0)
		            nextNode = head;
		        else
		            nextNode = nextNode.next;
				
		        hand = nextNode;
				
			}
		}
    	
    }

    public boolean goToNextElement() {
        if (isEmpty())
            return false;
        index = (index + 1) % numberOfElements;
        if (index == 0)
            nextNode = head;
        else
            nextNode = nextNode.next;
        return true;
    }
    
    public PageTableEntry getnextNodeData() {
        return nextNode.data;
    }

    public void setnextNodeData(PageTableEntry data) {
        nextNode.data = data;
    }


}
