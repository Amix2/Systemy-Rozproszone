package sr.ice.server;
// **********************************************************************
//
// Copyright (c) 2003-2016 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

import com.zeroc.Ice.Current;

import Demo.Calc;

public class CalcI implements Calc //zmiana w wersji 3.7
{
	private static final long serialVersionUID = -2448962912780867770L;

	@Override
	public long add(int a, int b, Current __current) 
	{
		System.out.println("ADD: a = " + a + ", b = " + b + ", result = " + (a+b));
		
		if(a > 1000 || b > 1000) {
			try { Thread.sleep(6000); } catch(java.lang.InterruptedException ex) { } 
		}
		
		if(__current.ctx.values().size() > 0) System.out.println("There are some properties in the context");
		
		return a + b;
	}
	
	@Override
	public long subtract(int a, int b, Current __current) {
		return 0;
	}
}
