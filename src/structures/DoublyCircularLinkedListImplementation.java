package structures;

public class DoublyCircularLinkedListImplementation<T extends Comparable<T>> implements
		DoublyCicularLinkedList<T> {
	
	DLLNode<T> head;
	DLLNode<T> tail;
	DLLNode<T> curr;
	DLLNode<T> itr;
	DLLNode<T> itr2;
	boolean reset;
	int size;
	
	public DoublyCircularLinkedListImplementation() {
            head = null;
            tail = null;
            curr = null;
            reset = false;
            size = 0;
	}
	
	@Override
	public int size() {
            return size;
	}

	@Override
	public void add(T element) {
            DLLNode<T> nNode = new DLLNode<T>(element);
            if(head==null)
            {
            	nNode.setBack(nNode);
            	nNode.setForward(nNode);
            	head=nNode;
            	tail=nNode;
            	size++;
            }
            else
            {
            	nNode.setBack(tail);
            	tail.setForward(nNode);
            	nNode.setForward(head);
            	head.setBack(nNode);
            	tail=nNode;
            	size++;
            }
            
	}

	@Override
	public boolean remove(T element) {
		DLLNode<T> nNode = new DLLNode<T>(element);
		if(head.getInfo().equals(element))
		{
			tail.setForward(head.getForward());
			head.getForward().setBack(tail);
			head=head.getForward();
			size--;
			return true;
		}
		else
		{
			DLLNode<T> temp = head;
			for(int i=1;i<size();i++)
			{
				temp.getForward();
				if(temp.getInfo().equals(element))
				{
					temp.getBack().setForward(temp.getForward());
					temp.getForward().setBack(temp.getBack());
					size--;
					return true;
				}
			}
		}
            return false;
	}

	
	@Override
	public boolean contains(T element) {
            DLLNode<T> temp = head;
            for(int i=0;i<size();i++)
            {
            	if(temp.getInfo().equals(element))
            	{
            		return true;
            	}
            	else
            	{
            		temp=temp.getForward();
            	}
            }
            return false;
	}

	@Override
	public T get(T element) {
		DLLNode<T> temp = head;
		for(int i=0;i<size();i++)
		{
			if(temp.getInfo().equals(element))
			{
				return temp.getInfo();
			}
			else
			{
				temp=temp.getForward();
			}
		}
            return null;
	}

	@Override
	public void reset() {
            curr=this.head;
            reset=true;
            itr = curr;
            itr2=tail;
	}

	@Override
	public T getNext() {
		if(size()!=0 && reset)
		{
			DLLNode<T> t = itr;
			itr=itr.getForward();
			return (T) t.getInfo();
		}
            return null;
	}

	@Override
	public T getPrevious() {
            if(size()!=0 && reset)
            {
            	DLLNode<T> t = itr2;
            	itr2=itr2.getBack();
            	return (T) t.getInfo();
            }
            return null;
	}

}
