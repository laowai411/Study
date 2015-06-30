namespace MapSpider
{
    partial class Form1
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.textBox2 = new System.Windows.Forms.TextBox();
            this.gBtnConfig = new System.Windows.Forms.Button();
            this.gBtnStart = new System.Windows.Forms.Button();
            this.label3 = new System.Windows.Forms.Label();
            this.textBox3 = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.textBox4 = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.textBox5 = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.textBox6 = new System.Windows.Forms.TextBox();
            this.textBox7 = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(21, 28);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(59, 12);
            this.label1.TabIndex = 0;
            this.label1.Text = "地图根URL";
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(86, 24);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(302, 21);
            this.textBox1.TabIndex = 1;
            this.textBox1.Text = "http://";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(29, 57);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(53, 12);
            this.label2.TabIndex = 0;
            this.label2.Text = "配置文件";
            // 
            // textBox2
            // 
            this.textBox2.Location = new System.Drawing.Point(86, 53);
            this.textBox2.Name = "textBox2";
            this.textBox2.Size = new System.Drawing.Size(302, 21);
            this.textBox2.TabIndex = 1;
            this.textBox2.Text = "D:\\xx.xml";
            // 
            // gBtnConfig
            // 
            this.gBtnConfig.Location = new System.Drawing.Point(393, 52);
            this.gBtnConfig.Name = "gBtnConfig";
            this.gBtnConfig.Size = new System.Drawing.Size(66, 23);
            this.gBtnConfig.TabIndex = 2;
            this.gBtnConfig.Text = "选择...";
            this.gBtnConfig.UseVisualStyleBackColor = true;
            this.gBtnConfig.Click += new System.EventHandler(this.onBtnConfigClickHandler);
            // 
            // gBtnStart
            // 
            this.gBtnStart.Location = new System.Drawing.Point(198, 213);
            this.gBtnStart.Name = "gBtnStart";
            this.gBtnStart.Size = new System.Drawing.Size(75, 38);
            this.gBtnStart.TabIndex = 3;
            this.gBtnStart.Text = "开始";
            this.gBtnStart.UseVisualStyleBackColor = true;
            this.gBtnStart.Click += new System.EventHandler(this.onBtnStartClickHandler);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(29, 176);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(53, 12);
            this.label3.TabIndex = 4;
            this.label3.Text = "同时开启";
            // 
            // textBox3
            // 
            this.textBox3.Location = new System.Drawing.Point(83, 173);
            this.textBox3.Name = "textBox3";
            this.textBox3.Size = new System.Drawing.Size(32, 21);
            this.textBox3.TabIndex = 5;
            this.textBox3.Text = "5";
            this.textBox3.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(116, 177);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(89, 12);
            this.label4.TabIndex = 6;
            this.label4.Text = "个线程进行下载";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(31, 96);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(29, 12);
            this.label5.TabIndex = 7;
            this.label5.Text = "前缀";
            // 
            // textBox4
            // 
            this.textBox4.Location = new System.Drawing.Point(66, 93);
            this.textBox4.Name = "textBox4";
            this.textBox4.Size = new System.Drawing.Size(49, 21);
            this.textBox4.TabIndex = 8;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(31, 127);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(65, 12);
            this.label6.TabIndex = 9;
            this.label6.Text = "行列分隔符";
            // 
            // textBox5
            // 
            this.textBox5.Location = new System.Drawing.Point(99, 124);
            this.textBox5.Name = "textBox5";
            this.textBox5.Size = new System.Drawing.Size(40, 21);
            this.textBox5.TabIndex = 10;
            this.textBox5.Text = "_";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(196, 93);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(77, 12);
            this.label7.TabIndex = 11;
            this.label7.Text = "尝试最大行数";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(196, 124);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(77, 12);
            this.label8.TabIndex = 11;
            this.label8.Text = "尝试最大列数";
            // 
            // textBox6
            // 
            this.textBox6.Location = new System.Drawing.Point(277, 89);
            this.textBox6.Name = "textBox6";
            this.textBox6.Size = new System.Drawing.Size(53, 21);
            this.textBox6.TabIndex = 12;
            this.textBox6.Text = "80";
            // 
            // textBox7
            // 
            this.textBox7.Location = new System.Drawing.Point(277, 120);
            this.textBox7.Name = "textBox7";
            this.textBox7.Size = new System.Drawing.Size(53, 21);
            this.textBox7.TabIndex = 13;
            this.textBox7.Text = "80";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.ForeColor = System.Drawing.Color.Red;
            this.label9.Location = new System.Drawing.Point(275, 162);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(155, 12);
            this.label9.TabIndex = 14;
            this.label9.Text = "一个地图块最多尝试下载3次";
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.ForeColor = System.Drawing.Color.Red;
            this.label10.Location = new System.Drawing.Point(252, 186);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(215, 12);
            this.label10.TabIndex = 15;
            this.label10.Text = "连续5个地图块下载失败则认为下载完毕";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(484, 262);
            this.Controls.Add(this.label10);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.textBox7);
            this.Controls.Add(this.textBox6);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.textBox5);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.textBox4);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.textBox3);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.gBtnStart);
            this.Controls.Add(this.gBtnConfig);
            this.Controls.Add(this.textBox2);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.label1);
            this.Name = "Form1";
            this.Text = "MapSpider";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox textBox2;
        private System.Windows.Forms.Button gBtnConfig;
        private System.Windows.Forms.Button gBtnStart;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox textBox3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox textBox4;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox textBox5;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox textBox6;
        private System.Windows.Forms.TextBox textBox7;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.Label label10;
    }
}

