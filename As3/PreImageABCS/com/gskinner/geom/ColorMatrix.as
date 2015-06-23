﻿package com.gskinner.geom
{

    dynamic public class ColorMatrix extends Array
    {
        private static const IDENTITY_MATRIX:Array = [1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1];
        private static const LENGTH:Number = IDENTITY_MATRIX.length;
        private static const DELTA_INDEX:Array = [0, 0.01, 0.02, 0.04, 0.05, 0.06, 0.07, 0.08, 0.1, 0.11, 0.12, 0.14, 0.15, 0.16, 0.17, 0.18, 0.2, 0.21, 0.22, 0.24, 0.25, 0.27, 0.28, 0.3, 0.32, 0.34, 0.36, 0.38, 0.4, 0.42, 0.44, 0.46, 0.48, 0.5, 0.53, 0.56, 0.59, 0.62, 0.65, 0.68, 0.71, 0.74, 0.77, 0.8, 0.83, 0.86, 0.89, 0.92, 0.95, 0.98, 1, 1.06, 1.12, 1.18, 1.24, 1.3, 1.36, 1.42, 1.48, 1.54, 1.6, 1.66, 1.72, 1.78, 1.84, 1.9, 1.96, 2, 2.12, 2.25, 2.37, 2.5, 2.62, 2.75, 2.87, 3, 3.2, 3.4, 3.6, 3.8, 4, 4.3, 4.7, 4.9, 5, 5.5, 6, 6.5, 6.8, 7, 7.3, 7.5, 7.8, 8, 8.4, 8.7, 9, 9.4, 9.6, 9.8, 10];

        public function ColorMatrix(param1:Array = null)
        {
            param1 = fixMatrix(param1);
            copyMatrix(param1.length == LENGTH ? (param1) : (IDENTITY_MATRIX));
            return;
        }// end function

        public function adjustBrightness(param1:Number) : void
        {
            param1 = cleanValue(param1, 100);
            if (param1 == 0 || isNaN(param1))
            {
                return;
            }
            multiplyMatrix([1, 0, 0, 0, param1, 0, 1, 0, 0, param1, 0, 0, 1, 0, param1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1]);
            return;
        }// end function

        protected function multiplyMatrix(param1:Array) : void
        {
            var _loc_2:Array = null;
            var _loc_3:uint = 0;
            var _loc_4:uint = 0;
            var _loc_5:Number = NaN;
            var _loc_6:Number = NaN;
            _loc_2 = [];
            _loc_3 = 0;
            while (_loc_3 < 5)
            {
                
                _loc_4 = 0;
                while (_loc_4 < 5)
                {
                    
                    _loc_2[_loc_4] = this[_loc_4 + _loc_3 * 5];
                    _loc_4 = _loc_4 + 1;
                }
                _loc_4 = 0;
                while (_loc_4 < 5)
                {
                    
                    _loc_5 = 0;
                    _loc_6 = 0;
                    while (_loc_6 < 5)
                    {
                        
                        _loc_5 = _loc_5 + param1[_loc_4 + _loc_6 * 5] * _loc_2[_loc_6];
                        _loc_6 = _loc_6 + 1;
                    }
                    this[_loc_4 + _loc_3 * 5] = _loc_5;
                    _loc_4 = _loc_4 + 1;
                }
                _loc_3 = _loc_3 + 1;
            }
            return;
        }// end function

        public function adjustSaturation(param1:Number) : void
        {
            var _loc_2:Number = NaN;
            var _loc_3:Number = NaN;
            var _loc_4:Number = NaN;
            var _loc_5:Number = NaN;
            param1 = cleanValue(param1, 100);
            if (param1 == 0 || isNaN(param1))
            {
                return;
            }
            _loc_2 = 1 + (param1 > 0 ? (3 * param1 / 100) : (param1 / 100));
            _loc_3 = 0.3086;
            _loc_4 = 0.6094;
            _loc_5 = 0.082;
            multiplyMatrix([_loc_3 * (1 - _loc_2) + _loc_2, _loc_4 * (1 - _loc_2), _loc_5 * (1 - _loc_2), 0, 0, _loc_3 * (1 - _loc_2), _loc_4 * (1 - _loc_2) + _loc_2, _loc_5 * (1 - _loc_2), 0, 0, _loc_3 * (1 - _loc_2), _loc_4 * (1 - _loc_2), _loc_5 * (1 - _loc_2) + _loc_2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1]);
            return;
        }// end function

        public function reset() : void
        {
            var _loc_1:uint = 0;
            _loc_1 = 0;
            while (_loc_1 < LENGTH)
            {
                
                this[_loc_1] = IDENTITY_MATRIX[_loc_1];
                _loc_1 = _loc_1 + 1;
            }
            return;
        }// end function

        public function adjustColor(param1:Number, param2:Number, param3:Number, param4:Number) : void
        {
            adjustHue(param4);
            adjustContrast(param2);
            adjustBrightness(param1);
            adjustSaturation(param3);
            return;
        }// end function

        public function clone() : ColorMatrix
        {
            return new ColorMatrix(this);
        }// end function

        public function toArray() : Array
        {
            return slice(0, 20);
        }// end function

        protected function cleanValue(param1:Number, param2:Number) : Number
        {
            return Math.min(param2, Math.max(-param2, param1));
        }// end function

        public function adjustHue(param1:Number) : void
        {
            var _loc_2:Number = NaN;
            var _loc_3:Number = NaN;
            var _loc_4:Number = NaN;
            var _loc_5:Number = NaN;
            var _loc_6:Number = NaN;
            param1 = cleanValue(param1, 180) / 180 * Math.PI;
            if (param1 == 0 || isNaN(param1))
            {
                return;
            }
            _loc_2 = Math.cos(param1);
            _loc_3 = Math.sin(param1);
            _loc_4 = 0.213;
            _loc_5 = 0.715;
            _loc_6 = 0.072;
            multiplyMatrix([_loc_4 + _loc_2 * (1 - _loc_4) + _loc_3 * (-_loc_4), _loc_5 + _loc_2 * (-_loc_5) + _loc_3 * (-_loc_5), _loc_6 + _loc_2 * (-_loc_6) + _loc_3 * (1 - _loc_6), 0, 0, _loc_4 + _loc_2 * (-_loc_4) + _loc_3 * 0.143, _loc_5 + _loc_2 * (1 - _loc_5) + _loc_3 * 0.14, _loc_6 + _loc_2 * (-_loc_6) + _loc_3 * -0.283, 0, 0, _loc_4 + _loc_2 * (-_loc_4) + _loc_3 * (-(1 - _loc_4)), _loc_5 + _loc_2 * (-_loc_5) + _loc_3 * _loc_5, _loc_6 + _loc_2 * (1 - _loc_6) + _loc_3 * _loc_6, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1]);
            return;
        }// end function

        public function concat(param1:Array) : void
        {
            param1 = fixMatrix(param1);
            if (param1.length != LENGTH)
            {
                return;
            }
            multiplyMatrix(param1);
            return;
        }// end function

        protected function copyMatrix(param1:Array) : void
        {
            var _loc_2:Number = NaN;
            var _loc_3:uint = 0;
            _loc_2 = LENGTH;
            _loc_3 = 0;
            while (_loc_3 < _loc_2)
            {
                
                this[_loc_3] = param1[_loc_3];
                _loc_3 = _loc_3 + 1;
            }
            return;
        }// end function

        protected function fixMatrix(param1:Array = null) : Array
        {
            if (param1 == null)
            {
                return IDENTITY_MATRIX;
            }
            if (param1 is ColorMatrix)
            {
                param1 = param1.slice(0);
            }
            if (param1.length < LENGTH)
            {
                param1 = param1.slice(0, param1.length).concat(IDENTITY_MATRIX.slice(param1.length, LENGTH));
            }
            else if (param1.length > LENGTH)
            {
                param1 = param1.slice(0, LENGTH);
            }
            return param1;
        }// end function

        public function adjustContrast(param1:Number) : void
        {
            var _loc_2:Number = NaN;
            param1 = cleanValue(param1, 100);
            if (param1 == 0 || isNaN(param1))
            {
                return;
            }
            if (param1 < 0)
            {
                _loc_2 = 127 + param1 / 100 * 127;
            }
            else
            {
                _loc_2 = param1 % 1;
                if (_loc_2 == 0)
                {
                    _loc_2 = DELTA_INDEX[param1];
                }
                else
                {
                    _loc_2 = DELTA_INDEX[param1 << 0] * (1 - _loc_2) + DELTA_INDEX[(param1 << 0) + 1] * _loc_2;
                }
                _loc_2 = _loc_2 * 127 + 127;
            }
            multiplyMatrix([_loc_2 / 127, 0, 0, 0, 0.5 * (127 - _loc_2), 0, _loc_2 / 127, 0, 0, 0.5 * (127 - _loc_2), 0, 0, _loc_2 / 127, 0, 0.5 * (127 - _loc_2), 0, 0, 0, 1, 0, 0, 0, 0, 0, 1]);
            return;
        }// end function

        public function toString() : String
        {
            return "ColorMatrix [ " + this.join(" , ") + " ]";
        }// end function

    }
}
