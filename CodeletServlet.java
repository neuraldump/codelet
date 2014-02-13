package com.codelet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sapient.Do.METHODS;

/**
 * 
 * @author Senthu Sivasambu
 *
 *
 * Copyright [2014] [Senthu Sivasambu]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public abstract class CodeletServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String doActionPath = request.getPathInfo();
		if(doActionPath == null || "".equals(doActionPath)){
			response.getWriter().write("Requested service name is invalid");
		}
		doAction(Do.METHODS.DOGET, doActionPath, request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String doActionPath = request.getPathInfo();
		if(doActionPath == null || "".equals(doActionPath)){
			response.getWriter().write("Requested service name is invalid");
		}
		doAction(Do.METHODS.DOGET, doActionPath, request, response);
	}
	
	protected final void doAction(METHODS httpMethod,String doActionName,HttpServletRequest request,HttpServletResponse response){
	
		//the implementation specifics are important because it take Theta(n)
		//where n is number of declared methods to 'copy' and return new ones
		Method[] methods = getClass().getDeclaredMethods();
		for(Method m : methods){
			Do annotation = m.getAnnotation(Do.class);
			if(annotation != null){
				if(annotation.method().getName().equalsIgnoreCase(httpMethod.getName()) && 
				   annotation.action().equalsIgnoreCase(doActionName)){
					m.setAccessible(true);
					//if(m.isAccessible()){
						try {
							m.invoke(this,request, response);
							return;
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException  e) {
								try {
									response.getWriter().write("\n Codelet failed to identify the do action requested \n");
									response.getWriter().write(e.getMessage());
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							e.printStackTrace();
						}
					//}
				}
			}
		}
		
	}


}
