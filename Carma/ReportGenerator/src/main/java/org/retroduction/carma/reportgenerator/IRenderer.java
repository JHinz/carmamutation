/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator;

import java.io.Writer;

/**
 * @author arau
 *
 */
public interface IRenderer {

	void render(Object context, Writer writer) throws RendererException;

}
