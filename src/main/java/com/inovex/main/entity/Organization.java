package com.inovex.main.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.inovex.main.entity.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Device entity class
 *
 * @author Faruk Hossain
 *
 */
@Entity
@Table(name = "organizations")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class Organization extends BaseEntity implements Serializable {

    @Column(name = "org_name", unique = true)
    private String orgName;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "subcription_number")
    private long subscriptionNumber;

    @Column(name = "subcription_period")
    private long subscriptionPeriod;

    @Column(name = "website")
    private String website;

    @Column(name = "logo")
    private String logo;

    @Column(name = "about")
    private String about;

    @Column(name = "transport_type")
    private String transportType;

    @Column(name = "owner_name")
    private String ownerName;

    @OneToMany(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<User> users = new HashSet<>();

    @OneToMany(targetEntity = RegionModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<RegionModel> regions = new HashSet<>();

    @OneToMany(targetEntity = Market.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Market> mkt = new HashSet<>();

    @OneToMany(targetEntity = ProductModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ProductModel> productModel = new HashSet<>();

    @OneToMany(targetEntity = Category.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Category> categories = new HashSet<>();

    @OneToMany(targetEntity = Supplier.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Supplier> suppliers = new HashSet<>();

    @OneToMany(targetEntity = Employee.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Employee> employess = new HashSet<>();

    @OneToMany(targetEntity = Retail.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Retail> retails = new HashSet<>();

    @OneToMany(targetEntity = RetailType.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<RetailType> retailType = new HashSet<>();

    @OneToMany(targetEntity = AttendanceType.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AttendanceType> attendanceTypes = new HashSet<>();

    @OneToMany(targetEntity = RouteModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<RouteModel> routes = new HashSet<>();

    @OneToMany(targetEntity = UserType.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<UserType> userType = new HashSet<>();

    @OneToMany(targetEntity = Expense.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Expense> expense = new HashSet<>();

    @OneToMany(targetEntity = AreaModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AreaModel> areaModels = new HashSet<>();

    @OneToMany(targetEntity = Attendance.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Attendance> attendances = new HashSet<>();

    @OneToMany(targetEntity = AttendanceTime.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AttendanceTime> attendanceTimes = new HashSet<>();

    @OneToMany(targetEntity = AttendanceTimeSetup.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AttendanceTimeSetup> attendanceTimeSetups = new HashSet<>();

    @OneToMany(targetEntity = BrandModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<BrandModel> brandModels = new HashSet<>();

    @OneToMany(targetEntity = CollectionModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CollectionModel> collectionModels = new HashSet<>();

    @OneToMany(targetEntity = CurrentLocation.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CurrentLocation> CurrentLocations = new HashSet<>();

    @OneToMany(targetEntity = DayModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DayModel> dayModels = new HashSet<>();

    @OneToMany(targetEntity = Distributor.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Distributor> distributors = new HashSet<>();

    @OneToMany(targetEntity = ExpenseType.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ExpenseType> expenseTypes = new HashSet<>();

    @OneToMany(targetEntity = FirebaseToken.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<FirebaseToken> firebaseTokens = new HashSet<>();

    @OneToMany(targetEntity = Location.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Location> locations = new HashSet<>();

    @OneToMany(targetEntity = MeasurementUnit.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<MeasurementUnit> measurementUnits = new HashSet<>();

    @OneToMany(targetEntity = MonthlyOrderTarget.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<MonthlyOrderTarget> monthlyOrderTargets = new HashSet<>();

    @OneToMany(targetEntity = MonthlyTarget.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<MonthlyTarget> monthlyTargets = new HashSet<>();

    @OneToMany(targetEntity = Notification.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(targetEntity = OrderHistory.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<OrderHistory> orderHistory = new HashSet<>();

    @OneToMany(targetEntity = PurchaseProduct.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<PurchaseProduct> purchaseProducts = new HashSet<>();

    @OneToMany(targetEntity = RetailComplain.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<RetailComplain> retailComplains = new HashSet<>();

    @OneToMany(targetEntity = RetailVisit.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<RetailVisit> retailVisits = new HashSet<>();

    @OneToMany(targetEntity = ReturnOrder.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ReturnOrder> returnOrders = new HashSet<>();

    @OneToMany(targetEntity = ReturnProduct.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ReturnProduct> returnProducts = new HashSet<>();

    @OneToMany(targetEntity = TerritoryModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<TerritoryModel> territoryModels = new HashSet<>();

    @OneToMany(targetEntity = VisitTarget.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<VisitTarget> visitTargets = new HashSet<>();

    @OneToMany(targetEntity = ProductMapping.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ProductMapping> productMapping = new HashSet<>();

    @OneToMany(targetEntity = ProductSubCategory.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ProductSubCategory> productSubCategory = new HashSet<>();

    @OneToMany(targetEntity = CompanyModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CompanyModel> companyModel = new HashSet<>();

    @OneToMany(targetEntity = ProductBatch.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ProductBatch> productBatch = new HashSet<>();

    @OneToMany(targetEntity = Price.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Price> price = new HashSet<>();

    @OneToMany(targetEntity = Campaign.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Campaign> campaigns = new HashSet<>();

    @OneToMany(targetEntity = CampaignType.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CampaignType> campaignType = new HashSet<>();

    @OneToMany(targetEntity = CashBackCampaign.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CashBackCampaign> cashBackCampaign = new HashSet<>();

    @OneToMany(targetEntity = ProductWiseCampaign.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ProductWiseCampaign> productWiseCampaigns = new HashSet<>();

    @OneToMany(targetEntity = DistributorRequisition.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DistributorRequisition> distributorRequisition = new HashSet<>();

    @OneToMany(targetEntity = Menu.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Menu> menu = new HashSet<>();

    @OneToMany(targetEntity = Role.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Role> role = new HashSet<>();

    @OneToMany(targetEntity = LeaveModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<LeaveModel> leaveModel = new HashSet<>();

    @OneToMany(targetEntity = ReturnPurchaseProduct.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ReturnPurchaseProduct> returnPurchaseProducts = new HashSet<>();
    
    @OneToMany(targetEntity = OrderEcommerceModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<OrderEcommerceModel> orderEcommerceModel = new HashSet<>();

}
