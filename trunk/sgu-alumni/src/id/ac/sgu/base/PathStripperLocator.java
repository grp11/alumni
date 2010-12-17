package id.ac.sgu.base;

import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.locator.ResourceStreamLocator;

public class PathStripperLocator extends ResourceStreamLocator 
{
	public PathStripperLocator() 
	{
		
	}

	public IResourceStream locate(final Class clazz, final String path) {
		IResourceStream located = super.locate(clazz, trimFolders(path));
		if (located != null) {
			return located;
		}
		return super.locate(clazz, path);
	}

	private String trimFolders(String path) {
		return path.substring(path.lastIndexOf("/") + 1);
	}
}
