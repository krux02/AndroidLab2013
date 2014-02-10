package com.example.l2plabv0;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * User: arne Date: 17.11.13 Time: 15:35
 */
public class StaticData {
	public static List<Course> getCourseList() {

		/*
		String cid= "13ws-55503";
		//String url = "http://137.226.231.116/websites/ws2014/_vti_bin/L2PServices/API.svc/v1/viewAllHyperlink?accessToken=asdlk&cid="+cid;
		String url = "http://seoul.freehostia.com/file.json";
		//String url = "http://137.226.231.112:11401/websites/testRest/_vti_bin/L2PServices/Api.svc/v1/viewAllHyperlink?accesstoken=asdbcd&p=return%20this";
		
		List<Course> courseAndLinks = new ArrayList<Course>();


		try {
			String jsonFromServer = new NTLMAuthenticationGETorPOST().execute(url)
					.get();
			JSONObject jsonObjectFromServer = new JSONObject(jsonFromServer);
			JSONArray dataSetArray = jsonObjectFromServer
					.getJSONArray("dataSet");
			for (Integer c = 0; c < 3; ++c) {
                List<Link> listOfLinks = new ArrayList<Link>();
				for (int i = 0; i < dataSetArray.length(); ++i) {
					JSONObject singleLinkItem = dataSetArray.getJSONObject(i);
					String singleComment = singleLinkItem.getString("notes");
					String singleUrl = singleLinkItem.getString("url");
					String singleDescription= singleLinkItem.getString("description");

					listOfLinks.add(new Link(singleComment, singleUrl, singleDescription));
				}
				courseAndLinks.add(new Course("Course id: "+cid+c.toString(), listOfLinks));
			}
		} catch (Exception e) {
			CommonData.writeLog("Static Data Download", e);
		}
		//courseAndLinks.add(new Course("Newest L2P", listOfLinks));

		return courseAndLinks;
		
		// 
		
		List<Link> listOfLinks = new ArrayList<Link>();
		listOfLinks.add(new Link("Matlab", "http://en.wikipedia.org/wiki/MATLAB"));
		listOfLinks.add(new Link("Bayes' theorem", "http://en.wikipedia.org/wiki/Bayes"));
		courseAndLinks.add(new Course("Scientific computing",listOfLinks));
		
		return courseAndLinks;
		*/
		
		//*
		  return Arrays.asList( new
		  Course("languages for scientific computing", new Link("Matlab",
		  "http://en.wikipedia.org/wiki/MATLAB"), new Link("Mathematica",
		  "http://en.wikipedia.org/wiki/Mathematica"), new
		  Link("C programming language",
		  "http://en.wikipedia.org/wiki/C_%28language%29"), new
		  Link("numerical analysis",
		  "http://en.wikipedia.org/wiki/Numerical_analysis") ), new
		  Course("machine learning", new Link("Bayes' theorem",
		  "http://en.wikipedia.org/wiki/Bayes%27_theorem"), new
		  Link("Singular Value Decomposition",
		  "http://en.wikipedia.org/wiki/Singular_value_decomposition"), new
		  Link("Support vector machine",
		  "http://en.wikipedia.org/wiki/Support_vector_machine"), new
		  Link("k-means clustering",
		  "http://en.wikipedia.org/wiki/K-means_clustering"), new
		  Link("Regression analysis",
		  "http://en.wikipedia.org/wiki/Regression_analysis") ), new
		  Course("computer vision", new Link("image segmentation",
		  "http://en.wikipedia.org/wiki/Image_segmentation"), new
		  Link("face detection",
		  "http://en.wikipedia.org/wiki/Face_detection"), new
		  Link("pedestrian detection",
		  "http://en.wikipedia.org/wiki/Pedestrian_detection") ) );
		 // */
	}

	public static class Course {
		public String name;
		public List<Link> topics;

		
		public Course(String name, Link... listOfLinks) {
			this.name = name;
			this.topics = new ArrayList<Link>(Arrays.asList(listOfLinks));
		}

        @Override
        public String toString() {
            return "Course{" +
                    "name='" + name + '\'' +
                    ", topics=" + topics +
                    '}';
        }
    }

	public static class Link implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String description;
		public String url;
		public String name;
		public Boolean isBookmarked;
		public String group; // can be empty
		public Date lastModified;

		public Link(String description, String url, String name,
				Boolean bookmarked, String group, Date lastModified) {
			this.description = description;
			this.url = url;
			this.name = name;
			isBookmarked = bookmarked;
			this.group = group;
			this.lastModified = lastModified;
		}
		
		public Link(String description, String url) {
			this.description = "No user defined comments...";
			this.url = url;
			this.name = description;
			isBookmarked = false;
			this.group = "";
			this.lastModified = new Date();
		}

		public Link(String comments, String url, String description) {
			this.description = comments;
			this.url = url;
			this.name = description;
			isBookmarked = false;
			this.group = "";
			this.lastModified = new Date();
		}

        @Override
        public String toString() {
            return "Link{" +
                    "description='" + description + '\'' +
                    ", url='" + url + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
