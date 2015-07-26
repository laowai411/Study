package controller.scene
{
	import flash.events.Event;
	
	import model.scene.MapManager;
	import model.scene.SceneManager;
	
	/**
	 * 场景
	 * */
	public class SceneController
	{
		
		private static var gInstance:SceneController;
		
		public static function getInstance():SceneController
		{
			if(gInstance == null)
			{
				gInstance = new SceneController();
			}
			return gInstance;
		}
		
		public function SceneController()
		{
		}
		
		public function init():void
		{
			//下层地图改变
			MapManager.getInstance().addEventListener(MapManager.DOWN_MAP_CHANGE, onDownMapChangeHandler);
			//地图改变
			MapManager.getInstance().addEventListener(MapManager.MAP_CHANGE, onMapChangeHandler);
		}
		
		/**
		 * 地图改变
		 * */
		protected function onMapChangeHandler(event:Event):void
		{
			// TODO Auto-generated method stub
			
		}
		/**
		 * 下层地图改变
		 * */
		protected function onDownMapChangeHandler(event:Event):void
		{
			// TODO Auto-generated method stub
			
		}
		
		public function createScene():void
		{
			
		}
	}
}