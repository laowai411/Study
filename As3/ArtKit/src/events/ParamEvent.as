package events
{
	import flash.events.Event;

	public class ParamEvent extends Event
	{
		
		public var param:Object;
		
		public function ParamEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}