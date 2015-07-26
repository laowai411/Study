package controller
{
	import controller.scene.SceneController;

	public class MainController
	{
		
		private static var gInstance:MainController;
		
		public static function getInstance():MainController
		{
			if(gInstance == null)
			{
				gInstance = new MainController();
			}
			return gInstance;
		}
		
		public function MainController()
		{
			
		}
		
		public function init():void
		{
			SceneController.getInstance().init();
		}
		
		/**
		 * 启动
		 * */
		public function start():void
		{
			SceneController.getInstance().createScene();
		}
	}
}