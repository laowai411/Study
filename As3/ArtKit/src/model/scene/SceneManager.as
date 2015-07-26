package model.scene
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	
	
	/**
	 * 场景数据管理器
	 * */
	public class SceneManager extends EventDispatcher
	{
		
		private static var gInstance:SceneManager;
		
		public static function getInstance():SceneManager
		{
			if(gInstance == null)
			{
				gInstance = new SceneManager();
			}
			return gInstance;
		}
		
		public function SceneManager(target:IEventDispatcher=null)
		{
			super(target);
		}
		
	}
}