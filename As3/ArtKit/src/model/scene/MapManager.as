package model.scene
{
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	import flash.events.Event;
	
	
	/**
	 * 地图管理器
	 * */
	public class MapManager extends EventDispatcher
	{
		private static var gInstance:MapManager;
		
		public static function getInstance():MapManager
		{
			if(gInstance == null)
			{
				gInstance = new MapManager();
			}
			return gInstance;
		}
		
		/**
		 * 下层地图改变
		 * */
		public static const DOWN_MAP_CHANGE:String = "DOWN_MAP_CHANGE";
		/**
		 * 地图改变
		 * */
		public static const MAP_CHANGE:String = "MAP_CHANGE";
		/**
		 * 下层地图
		 * */
		private var gDownMapPath:String;
		/**
		 * 地图
		 * */
		private var gMapPath:String;
		
		public function MapManager(target:IEventDispatcher=null)
		{
			super(target);
		}
		
		/**
		 * 下层地图
		 * */
		public function set downMapPath(cusPath:String):void
		{
			if(cusPath != gDownMapPath)
			{
				gDownMapPath = cusPath;
				this.dispatchEvent(new Event(DOWN_MAP_CHANGE));
			}
		}
		/**
		 * 地图
		 * */
		public function set mapPath(cusPath:String):void
		{
			if(cusPath != gMapPath)
			{
				gMapPath = cusPath;
				this.dispatchEvent(new Event(MAP_CHANGE));
			}
		}
		/**
		 * 下层地图id
		 * */
		public function get downMapPath():String
		{
			return downMapPath;	
		}
		/**
		 * 地图id
		 * */
		public function get mapPath():String
		{
			return mapPath;
		}
	}
}