
/** test unitaire pour la classe  ErrorAttributeOptions */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;

import java.util.EnumSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ErrorAttributeOptionsTest {

    @Mock
    private Set<Include> includes;

    private ErrorAttributeOptions errorAttributeOptions;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        errorAttributeOptions = new ErrorAttributeOptions(includes);
    }

    @Test
    public void testIsIncluded() {
        // Mocking
        Include include1 = Include.EXCEPTION;
        Include include2 = Include.MESSAGE;
        when(includes.contains(include1)).thenReturn(true);
        when(includes.contains(include2)).thenReturn(false);

        // Testing
        assertTrue(errorAttributeOptions.isIncluded(include1));
        assertFalse(errorAttributeOptions.isIncluded(include2));
    }

    @Test
    public void testGetIncludes() {
        // Mocking
        when(includes.isEmpty()).thenReturn(false);
        when(includes.copyOf(includes)).thenReturn(EnumSet.allOf(Include.class));

        // Testing
        assertEquals(EnumSet.allOf(Include.class), errorAttributeOptions.getIncludes());
    }

    @Test
    public void testIncluding() {
        // Mocking
        Include include1 = Include.EXCEPTION;
        Include include2 = Include.MESSAGE;
        EnumSet<Include> updated = EnumSet.of(include1, include2);
        when(includes.isEmpty()).thenReturn(false);
        when(includes.addAll(Arrays.asList(include1, include2))).thenReturn(true);
        when(includes.copyOf(includes)).thenReturn(updated);

        // Testing
        ErrorAttributeOptions updatedOptions = errorAttributeOptions.including(include1, include2);
        assertEquals(updated, updatedOptions.getIncludes());
    }

    @Test
    public void testExcluding() {
        // Mocking
        Include include1 = Include.EXCEPTION;
        Include include2 = Include.MESSAGE;
        EnumSet<Include> updated = EnumSet.of(include1);
        when(includes.isEmpty()).thenReturn(false);
        when(includes.removeAll(Arrays.asList(include1, include2))).thenReturn(true);
        when(includes.copyOf(includes)).thenReturn(updated);

        // Testing
        ErrorAttributeOptions updatedOptions = errorAttributeOptions.excluding(include1, include2);
        assertEquals(updated, updatedOptions.getIncludes());
    }

    @Test
    public void testDefaults() {
        // Mocking
        when(includes.isEmpty()).thenReturn(true);
        when(includes.copyOf(includes)).thenReturn(EnumSet.noneOf(Include.class));

        // Testing
        ErrorAttributeOptions defaultOptions = ErrorAttributeOptions.defaults();
        assertEquals(EnumSet.noneOf(Include.class), defaultOptions.getIncludes());
    }

    @Test
    public void testOf() {
        // Mocking
        Include include1 = Include.EXCEPTION;
        Include include2 = Include.MESSAGE;
        EnumSet<Include> updated = EnumSet.of(include1, include2);
        when(includes.isEmpty()).thenReturn(false);
        when(includes.copyOf(includes)).thenReturn(updated);

        // Testing
        ErrorAttributeOptions updatedOptions = ErrorAttributeOptions.of(include1, include2);
        assertEquals(updated, updatedOptions.getIncludes());
    }

    @Test
    public void testOfCollection() {
        // Mocking
        Include include1 = Include.EXCEPTION;
        Include include2 = Include.MESSAGE;
       
