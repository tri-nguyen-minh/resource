USE [master]

GO
ALTER DATABASE [ResourceDB] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ResourceDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ResourceDB] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [ResourceDB] SET ANSI_NULLS OFF
GO
ALTER DATABASE [ResourceDB] SET ANSI_PADDING OFF
GO
ALTER DATABASE [ResourceDB] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [ResourceDB] SET ARITHABORT OFF
GO
ALTER DATABASE [ResourceDB] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [ResourceDB] SET AUTO_CREATE_STATISTICS ON
GO
ALTER DATABASE [ResourceDB] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [ResourceDB] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [ResourceDB] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [ResourceDB] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [ResourceDB] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [ResourceDB] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [ResourceDB] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [ResourceDB] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [ResourceDB] SET  DISABLE_BROKER
GO
ALTER DATABASE [ResourceDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [ResourceDB] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [ResourceDB] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [ResourceDB] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [ResourceDB] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [ResourceDB] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [ResourceDB] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [ResourceDB] SET  READ_WRITE
GO
ALTER DATABASE [ResourceDB] SET RECOVERY SIMPLE
GO
ALTER DATABASE [ResourceDB] SET  MULTI_USER
GO
ALTER DATABASE [ResourceDB] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [ResourceDB] SET DB_CHAINING OFF
GO
USE [ResourceDB]
GO
/****** Object:  Table [dbo].[TblCategory]    Script Date: 07/19/2020 11:49:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TblCategory](
	[CategoryId] [int] NOT NULL,
	[Category] [varchar](50) NOT NULL,
 CONSTRAINT [PK_TblCategory] PRIMARY KEY CLUSTERED 
(
	[CategoryId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[TblCategory] ([CategoryId], [Category]) VALUES (1, N'Room')
INSERT [dbo].[TblCategory] ([CategoryId], [Category]) VALUES (2, N'Desk')
INSERT [dbo].[TblCategory] ([CategoryId], [Category]) VALUES (3, N'Chair')
INSERT [dbo].[TblCategory] ([CategoryId], [Category]) VALUES (4, N'Laptop')
/****** Object:  Table [dbo].[TblStatus]    Script Date: 07/19/2020 11:49:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TblStatus](
	[StatusId] [int] NOT NULL,
	[Status] [varchar](50) NOT NULL,
 CONSTRAINT [PK_TblStatus] PRIMARY KEY CLUSTERED 
(
	[StatusId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[TblStatus] ([StatusId], [Status]) VALUES (1, N'New')
INSERT [dbo].[TblStatus] ([StatusId], [Status]) VALUES (2, N'Active')
INSERT [dbo].[TblStatus] ([StatusId], [Status]) VALUES (3, N'Inactive')
INSERT [dbo].[TblStatus] ([StatusId], [Status]) VALUES (4, N'Accepted')
INSERT [dbo].[TblStatus] ([StatusId], [Status]) VALUES (5, N'Deleted')
/****** Object:  Table [dbo].[TblRole]    Script Date: 07/19/2020 11:49:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TblRole](
	[RoleId] [int] NOT NULL,
	[Role] [nchar](10) NOT NULL,
 CONSTRAINT [PK_TblRole] PRIMARY KEY CLUSTERED 
(
	[RoleId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[TblRole] ([RoleId], [Role]) VALUES (1, N'Manager   ')
INSERT [dbo].[TblRole] ([RoleId], [Role]) VALUES (2, N'Leader    ')
INSERT [dbo].[TblRole] ([RoleId], [Role]) VALUES (3, N'Employee  ')
/****** Object:  Table [dbo].[TblResource]    Script Date: 07/19/2020 11:49:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TblResource](
	[id] [varchar](50) NOT NULL,
	[itemName] [varchar](max) NOT NULL,
	[color] [varchar](50) NOT NULL,
	[categoryId] [int] NOT NULL,
	[roleId] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[statusId] [int] NOT NULL,
 CONSTRAINT [PK_TblResource] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R1', N'Conference Room', N'None', 1, 2, 1, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R10', N'Room 202', N'None', 1, 3, 1, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R11', N'Room 203', N'None', 1, 3, 1, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R12', N'Room 204', N'None', 1, 2, 1, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R13', N'Room 205', N'None', 1, 3, 1, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R14', N'Glass Top Desk', N'Blue', 2, 2, 5, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R15', N'Office Chair', N'White', 3, 3, 30, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R16', N'Wooden Chair', N'Orange', 3, 3, 100, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R17', N'Rocking Chair', N'Light Brown', 3, 2, 2, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R18', N'Laptop ASUS ZenBook Pro', N'Black', 4, 2, 10, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R19', N'Laptop Dell Inspiron 3493', N'Black', 4, 3, 20, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R2', N'Room 101', N'None', 1, 2, 1, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R20', N'Laptop LG Gram 2020', N'Gray', 4, 3, 20, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R21', N'Long Desk', N'White', 2, 3, 20, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R3', N'Room 102', N'None', 1, 2, 1, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R4', N'Room 103', N'None', 1, 2, 1, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R5', N'Room 104', N'None', 1, 2, 1, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R6', N'Room 105', N'None', 1, 2, 1, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R7', N'Wooden Large Desk', N'Brown', 2, 3, 20, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R8', N'Z-Shape Desk', N'Gray', 2, 3, 10, 2)
INSERT [dbo].[TblResource] ([id], [itemName], [color], [categoryId], [roleId], [quantity], [statusId]) VALUES (N'R9', N'Room 201', N'None', 1, 3, 1, 2)
/****** Object:  Table [dbo].[TblAccount]    Script Date: 07/19/2020 11:49:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TblAccount](
	[id] [varchar](50) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[name] [varchar](max) NOT NULL,
	[phone] [varchar](12) NOT NULL,
	[address] [varchar](max) NOT NULL,
	[createDate] [date] NOT NULL,
	[roleId] [int] NOT NULL,
	[statusId] [int] NOT NULL,
 CONSTRAINT [PK_TblAccount] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[TblAccount] ([id], [password], [name], [phone], [address], [createDate], [roleId], [statusId]) VALUES (N'se140329@fpt.edu.vn', N'sa', N'Tri Nguyen', N'0947009497', N'abc', CAST(0x44410B00 AS Date), 1, 2)
INSERT [dbo].[TblAccount] ([id], [password], [name], [phone], [address], [createDate], [roleId], [statusId]) VALUES (N'se140329@gmail.com', N'sa', N'Tri', N'0947009497', N'abc', CAST(0x44410B00 AS Date), 2, 2)
INSERT [dbo].[TblAccount] ([id], [password], [name], [phone], [address], [createDate], [roleId], [statusId]) VALUES (N'tri140329@gmail.com', N'sa', N'Tri Minh Nguyen', N'0947009497', N'abc', CAST(0x44410B00 AS Date), 3, 2)
/****** Object:  Table [dbo].[TblBooking]    Script Date: 07/19/2020 11:49:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TblBooking](
	[bookingId] [varchar](50) NOT NULL,
	[userId] [varchar](50) NOT NULL,
	[bookDate] [date] NOT NULL,
	[statusId] [int] NOT NULL,
 CONSTRAINT [PK_TblBooking] PRIMARY KEY CLUSTERED 
(
	[bookingId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[TblBooking] ([bookingId], [userId], [bookDate], [statusId]) VALUES (N'B1', N'tri140329@gmail.com', CAST(0x45410B00 AS Date), 3)
INSERT [dbo].[TblBooking] ([bookingId], [userId], [bookDate], [statusId]) VALUES (N'B2', N'se140329@gmail.com', CAST(0x45410B00 AS Date), 5)
INSERT [dbo].[TblBooking] ([bookingId], [userId], [bookDate], [statusId]) VALUES (N'B3', N'tri140329@gmail.com', CAST(0x46410B00 AS Date), 4)
INSERT [dbo].[TblBooking] ([bookingId], [userId], [bookDate], [statusId]) VALUES (N'B4', N'tri140329@gmail.com', CAST(0x46410B00 AS Date), 4)
INSERT [dbo].[TblBooking] ([bookingId], [userId], [bookDate], [statusId]) VALUES (N'B5', N'tri140329@gmail.com', CAST(0x47410B00 AS Date), 4)
INSERT [dbo].[TblBooking] ([bookingId], [userId], [bookDate], [statusId]) VALUES (N'B6', N'tri140329@gmail.com', CAST(0x57410B00 AS Date), 1)
INSERT [dbo].[TblBooking] ([bookingId], [userId], [bookDate], [statusId]) VALUES (N'B7', N'se140329@gmail.com', CAST(0x57410B00 AS Date), 3)
/****** Object:  Table [dbo].[TblBookingDetail]    Script Date: 07/19/2020 11:49:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TblBookingDetail](
	[id] [varchar](50) NOT NULL,
	[bookingId] [varchar](50) NOT NULL,
	[resourceId] [varchar](50) NOT NULL,
	[fromDate] [date] NOT NULL,
	[toDate] [date] NOT NULL,
	[quantity] [int] NOT NULL,
 CONSTRAINT [PK_TblBookDetail] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[TblBookingDetail] ([id], [bookingId], [resourceId], [fromDate], [toDate], [quantity]) VALUES (N'D1', N'B1', N'R10', CAST(0x84410B00 AS Date), CAST(0x88410B00 AS Date), 1)
INSERT [dbo].[TblBookingDetail] ([id], [bookingId], [resourceId], [fromDate], [toDate], [quantity]) VALUES (N'D10', N'B7', N'R5', CAST(0x58410B00 AS Date), CAST(0x58410B00 AS Date), 1)
INSERT [dbo].[TblBookingDetail] ([id], [bookingId], [resourceId], [fromDate], [toDate], [quantity]) VALUES (N'D2', N'B1', N'R16', CAST(0x6E410B00 AS Date), CAST(0x7A410B00 AS Date), 15)
INSERT [dbo].[TblBookingDetail] ([id], [bookingId], [resourceId], [fromDate], [toDate], [quantity]) VALUES (N'D3', N'B2', N'R1', CAST(0x28410B00 AS Date), CAST(0xFE410B00 AS Date), 1)
INSERT [dbo].[TblBookingDetail] ([id], [bookingId], [resourceId], [fromDate], [toDate], [quantity]) VALUES (N'D4', N'B3', N'R20', CAST(0x47410B00 AS Date), CAST(0x47410B00 AS Date), 9)
INSERT [dbo].[TblBookingDetail] ([id], [bookingId], [resourceId], [fromDate], [toDate], [quantity]) VALUES (N'D5', N'B3', N'R16', CAST(0x47410B00 AS Date), CAST(0x70410B00 AS Date), 14)
INSERT [dbo].[TblBookingDetail] ([id], [bookingId], [resourceId], [fromDate], [toDate], [quantity]) VALUES (N'D6', N'B4', N'R15', CAST(0x47410B00 AS Date), CAST(0x78410B00 AS Date), 30)
INSERT [dbo].[TblBookingDetail] ([id], [bookingId], [resourceId], [fromDate], [toDate], [quantity]) VALUES (N'D7', N'B5', N'R9', CAST(0x48410B00 AS Date), CAST(0x48410B00 AS Date), 1)
INSERT [dbo].[TblBookingDetail] ([id], [bookingId], [resourceId], [fromDate], [toDate], [quantity]) VALUES (N'D8', N'B5', N'R16', CAST(0x53410B00 AS Date), CAST(0x5D410B00 AS Date), 17)
INSERT [dbo].[TblBookingDetail] ([id], [bookingId], [resourceId], [fromDate], [toDate], [quantity]) VALUES (N'D9', N'B6', N'R16', CAST(0x5A410B00 AS Date), CAST(0x5C410B00 AS Date), 7)
/****** Object:  ForeignKey [FK_TblResource_TblCategory]    Script Date: 07/19/2020 11:49:08 ******/
ALTER TABLE [dbo].[TblResource]  WITH CHECK ADD  CONSTRAINT [FK_TblResource_TblCategory] FOREIGN KEY([categoryId])
REFERENCES [dbo].[TblCategory] ([CategoryId])
GO
ALTER TABLE [dbo].[TblResource] CHECK CONSTRAINT [FK_TblResource_TblCategory]
GO
/****** Object:  ForeignKey [FK_TblResource_TblRole]    Script Date: 07/19/2020 11:49:08 ******/
ALTER TABLE [dbo].[TblResource]  WITH CHECK ADD  CONSTRAINT [FK_TblResource_TblRole] FOREIGN KEY([roleId])
REFERENCES [dbo].[TblRole] ([RoleId])
GO
ALTER TABLE [dbo].[TblResource] CHECK CONSTRAINT [FK_TblResource_TblRole]
GO
/****** Object:  ForeignKey [FK_TblResource_TblStatus]    Script Date: 07/19/2020 11:49:08 ******/
ALTER TABLE [dbo].[TblResource]  WITH CHECK ADD  CONSTRAINT [FK_TblResource_TblStatus] FOREIGN KEY([statusId])
REFERENCES [dbo].[TblStatus] ([StatusId])
GO
ALTER TABLE [dbo].[TblResource] CHECK CONSTRAINT [FK_TblResource_TblStatus]
GO
/****** Object:  ForeignKey [FK_TblAccount_TblRole]    Script Date: 07/19/2020 11:49:08 ******/
ALTER TABLE [dbo].[TblAccount]  WITH CHECK ADD  CONSTRAINT [FK_TblAccount_TblRole] FOREIGN KEY([roleId])
REFERENCES [dbo].[TblRole] ([RoleId])
GO
ALTER TABLE [dbo].[TblAccount] CHECK CONSTRAINT [FK_TblAccount_TblRole]
GO
/****** Object:  ForeignKey [FK_TblAccount_TblStatus]    Script Date: 07/19/2020 11:49:08 ******/
ALTER TABLE [dbo].[TblAccount]  WITH CHECK ADD  CONSTRAINT [FK_TblAccount_TblStatus] FOREIGN KEY([statusId])
REFERENCES [dbo].[TblStatus] ([StatusId])
GO
ALTER TABLE [dbo].[TblAccount] CHECK CONSTRAINT [FK_TblAccount_TblStatus]
GO
/****** Object:  ForeignKey [FK_TblBooking_TblAccount]    Script Date: 07/19/2020 11:49:08 ******/
ALTER TABLE [dbo].[TblBooking]  WITH CHECK ADD  CONSTRAINT [FK_TblBooking_TblAccount] FOREIGN KEY([userId])
REFERENCES [dbo].[TblAccount] ([id])
GO
ALTER TABLE [dbo].[TblBooking] CHECK CONSTRAINT [FK_TblBooking_TblAccount]
GO
/****** Object:  ForeignKey [FK_TblBooking_TblStatus]    Script Date: 07/19/2020 11:49:08 ******/
ALTER TABLE [dbo].[TblBooking]  WITH CHECK ADD  CONSTRAINT [FK_TblBooking_TblStatus] FOREIGN KEY([statusId])
REFERENCES [dbo].[TblStatus] ([StatusId])
GO
ALTER TABLE [dbo].[TblBooking] CHECK CONSTRAINT [FK_TblBooking_TblStatus]
GO
/****** Object:  ForeignKey [FK_TblBookDetail_TblBooking]    Script Date: 07/19/2020 11:49:08 ******/
ALTER TABLE [dbo].[TblBookingDetail]  WITH CHECK ADD  CONSTRAINT [FK_TblBookDetail_TblBooking] FOREIGN KEY([bookingId])
REFERENCES [dbo].[TblBooking] ([bookingId])
GO
ALTER TABLE [dbo].[TblBookingDetail] CHECK CONSTRAINT [FK_TblBookDetail_TblBooking]
GO
/****** Object:  ForeignKey [FK_TblBookDetail_TblResource]    Script Date: 07/19/2020 11:49:08 ******/
ALTER TABLE [dbo].[TblBookingDetail]  WITH CHECK ADD  CONSTRAINT [FK_TblBookDetail_TblResource] FOREIGN KEY([resourceId])
REFERENCES [dbo].[TblResource] ([id])
GO
ALTER TABLE [dbo].[TblBookingDetail] CHECK CONSTRAINT [FK_TblBookDetail_TblResource]
GO
