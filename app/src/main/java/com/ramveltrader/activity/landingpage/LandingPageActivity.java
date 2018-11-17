package com.ramveltrader.activity.landingpage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ramveltrader.R;
import com.ramveltrader.activity.BaseAppCompatActivity;
import com.ramveltrader.data.SessionStore;
import com.ramveltrader.data.network.models.Address;
import com.ramveltrader.data.network.models.CartRequest;
import com.ramveltrader.data.network.models.CartResponse;
import com.ramveltrader.data.network.models.ProductResponse;
import com.ramveltrader.fragments.address.ShippingAddressFragment;
import com.ramveltrader.fragments.category.CategoryFragment;
import com.ramveltrader.fragments.comingsoon.ComingSoonFragment;
import com.ramveltrader.fragments.home.HomeFragment;
import com.ramveltrader.fragments.mycart.MyCartFragment;
import com.ramveltrader.fragments.product.ProductListFragment;
import com.ramveltrader.fragments.product.detail.ProductDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingPageActivity extends BaseAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LandingPageMvpView, View.OnClickListener  {

    public static final int FRAGMENT_DEFAULT = 1;
    public static final int FRAGMENT_HOME = 2;
    public static final int FRAGMENT_CATEGORY = 3;
    public static final int FRAGMENT_DETAIL_PRODUCT_LIST = 4;
    public static final int FRAGMENT_PRODUCT_DETAILS = 5;
    public static final int FRAGMENT_SHIPPING_ADDRESS = 6;
    public static final int FRAGMENT_MY_CART = 7;
    public static final int FRAGMENT_COMING_SOON = 0;

    private LandingPageMvpPresenter<LandingPageMvpView> mPresenter = null;

    TextView mItemCount;

    Address address = null;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    AppBarLayout.LayoutParams mParams;

    @BindView(R.id.search_bar_header)
    LinearLayout mLinearLayoutSearchBar;

    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_landing_page);
        setUnBinder(ButterKnife.bind(this));

        // initialize to set default value
        setSupportActionBar(mToolbar);

        // make config changes
        mParams = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
        mPresenter = new LandingPagePresenter<>();
        mPresenter.onAttach(this);

        // right place to set hamburger icon toggle
        setUpToggle(mToolbar);

        // add navigation view listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //load default
        displayView(FRAGMENT_HOME, "", false);
        // get cart count
        mPresenter.getCartList();
    }

    public void displayView(int position, String aTitle, boolean addToBackstack) {
        if(position == FRAGMENT_HOME) {
            mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                    | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            mLinearLayoutSearchBar.setVisibility(View.VISIBLE);
        } else {
            mParams.setScrollFlags(0);
            mLinearLayoutSearchBar.setVisibility(View.GONE);
        }
        Fragment fragment = null;
        String title = null;
        switch (position) {
            case FRAGMENT_HOME:
                title = "Home";
                fragment = HomeFragment.newInstance(title);
                break;
            case FRAGMENT_CATEGORY:
                fragment = CategoryFragment.newInstance(aTitle);
                break;
            case FRAGMENT_DETAIL_PRODUCT_LIST:
                fragment = ProductListFragment.newInstance(aTitle);
                break;
            case FRAGMENT_PRODUCT_DETAILS:
                title = "";
                fragment = ProductDetailFragment.newInstance(aTitle);
                break;
            case FRAGMENT_SHIPPING_ADDRESS:
                title = "";
                fragment = ShippingAddressFragment.newInstance(aTitle);
                break;
            case FRAGMENT_MY_CART:
                title = "";
                fragment = MyCartFragment.newInstance(aTitle);
                break;
            case FRAGMENT_DEFAULT:
            default:
                title = "Coming Soon";
                fragment = ComingSoonFragment.newInstance(aTitle);
                break;
        }
        if (null != fragment) {
            switchFragment(fragment, title, addToBackstack);
        }
    }

    public void switchFragment(Fragment fragment, String title, boolean aAddtoBackstack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        String backStateName = ft.getClass().getName();
        //ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        if (aAddtoBackstack)
            ft.addToBackStack(backStateName);
        ft.commit();
    }

//    @OnClick(R.id.item_counter)
//    public void onCartClick() {
//        displayView(LandingPageActivity.FRAGMENT_MY_CART, "My Cart", true);
//    }


//    @Override
//    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() == 0) {
//            return;
//        }
//        getFragmentManager().popBackStack();
//    }

    @Override
    public void doIncrementCartCount(Integer count) {
        count +=1;
        updateCartBadge(count);
    }

    @Override
    public void updateCartBadge(int qty) {
        String value = mItemCount.getText().toString();
        if(value.equals("")) {
            mItemCount.setText(String.valueOf(qty));
            return;
        }
        Integer count = Integer.parseInt(value);
        count += qty;
        mItemCount.setText(String.valueOf(count));
    }

    @Override
    public void updateAddress(Address address) {
        this.address = address;
    }

    @Override
    public void addCartToMyAccount(ProductResponse response, String quantity) {
        Toast.makeText(this, "Adding to My Cart", Toast.LENGTH_SHORT).show();
        Integer q = Integer.parseInt(quantity);
        CartRequest.CartItem cartItem = new CartRequest.CartItem(SessionStore.quoteId, response.getSku(), q);
        CartRequest request = new CartRequest(cartItem);
        mPresenter.addCart(request);
    }

    @Override
    public void cartAddedCallback(CartResponse cartResponse) {
        //updateCartBadge(cartResponse.getQty());
        updateCartBadge(1);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public String getCount() {
        return mItemCount.getText().toString();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        SessionStore.sSelectedMenu = menuItem.getTitle().toString();
        switch (menuItem.getItemId()) {
            case R.id.menu_home:
                displayView(FRAGMENT_HOME, menuItem.getTitle().toString(), true);
                break;
            case R.id.retail:
            case R.id.whole_sale:
                displayView(FRAGMENT_CATEGORY, menuItem.getTitle().toString(), true);
                break;
            default:
                displayView(LandingPageActivity.FRAGMENT_COMING_SOON, "Coming Soon", false);
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /***
     * Set up Hamburger Icon toggle
     * @param toolbar
     */
    public void setUpToggle(final Toolbar toolbar) {
        final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = menuItem.getActionView();
        actionView.setOnClickListener(this);
        mItemCount = actionView.findViewById(R.id.cart_badge);

        // config search view
        MenuItem searchView = menu.findItem( R.id.action_search);
        mSearchView = (SearchView) searchView.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        // Detect SearchView icon clicks
        /*searchView.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                //Nothing to do here
                setItemsVisibility(menu, item, false);
                return true; // Return true to expand action view
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                setItemsVisibility(menu, item, true);
                return true; // Return true to collapse action view
            }
        });*/
        return super.onCreateOptionsMenu(menu);
    }

    /*private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i=0; i<menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) item.setVisible(visible);
        }
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_cart:
                displayView(LandingPageActivity.FRAGMENT_MY_CART, "My Cart ("+mItemCount.getText()+")", false);
            break;
        }
    }
}
