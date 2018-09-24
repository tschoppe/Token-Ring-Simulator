package tokenring;

import java.util.LinkedList;

import structures.DoublyCicularLinkedList;
import structures.DoublyCircularLinkedListImplementation;

public class WorkstationImplementation extends Workstation {
	
	NetworkInterface net;
	LinkedList<Message> q;
	Workstation curr;
	private DoublyCicularLinkedList<Workstation> ring;

	public WorkstationImplementation(NetworkInterface nic) {
            net = nic;
            q = new LinkedList<Message>();
            this.ring = new DoublyCircularLinkedListImplementation<Workstation>();	
            this.curr = null;
	}

	public NetworkInterface getNIC() {
            return this.net;
	}
	
	@Override
	public int compareTo(Workstation o) {
            if(this.getId()>o.getId())
            {
            	return 1;
            }
            else if(this.getId()<o.getId())
            {
            	return -1;
            }
            else
            {
            	return 0;
            }
	}
	
	@Override
	public boolean equals(Object obj) {
		Workstation work = (Workstation) obj;
            if(this.getId()==work.getId())
            {
            	return true;
            }
            return false;
	}

	public void sendMessage(Message m) {
            q.add(m);
	}

	@Override
	public void tick() {
		NetworkInterface nic = net;
		if(this.getNIC().hasFrame())
		{
			Frame frame = nic.read();
			
			if(frame.isTokenFrame())
			{
				if(!q.isEmpty())
				{
					Message m = q.removeFirst();
					DataFrame df = new DataFrame(m);
					nic.write(df);
					incMsgSent();
				}
				else
				{
					nic.write(frame);
				}
			}
			else if(frame.isDataFrame())
			{
				DataFrame df = (DataFrame) frame;
				Message m = df.getMessage();
				if(m.getReceiver()==this.getId())
				{
					m.toString();
					incMsgRcvd();
					df.setReceived(true);
					nic.write(df);
					
				}
				else if(m.getSender()==this.getId() && df.wasReceived())
				{
					m.toString();
					nic.write(TokenFrame.TOKEN);
					incMsgDelivered();
				} 
				else if(m.getSender()==this.getId() && !df.wasReceived())
				{
					m.toString();
					nic.write(TokenFrame.TOKEN);
				}
				else if(m.getReceiver()!=this.getId())
				{
					nic.write(df);
				}
			}
		}
	}

}
